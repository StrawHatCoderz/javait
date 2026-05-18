package com.javait.services;

import com.javait.models.GithubUser;
import com.javait.models.TokenPayload;
import com.javait.models.User;

import com.javait.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final TokenService tokenService;
  private final GithubOAuthService oAuthService;

  public AuthService(TokenService tokenService,
                     UserRepository userRepository,
                     GithubOAuthService oAuthService) {
    this.userRepository = userRepository;
    this.tokenService = tokenService;
    this.oAuthService = oAuthService;
  }

  public Optional<TokenPayload> getCurrentSessionUser(String token) {
    return tokenService.parse(token);
  }

  public String loginWithGithub(String code) throws IOException, InterruptedException {
    GithubUser githubUser = oAuthService.fetchUserDetails(code);

    Optional<User> existing = userRepository.findById(githubUser.id());

    User user = existing.orElseGet(() -> userRepository
                    .save(new User(
                            githubUser.id(),
                            githubUser.name(),
                            githubUser.avatarUrl())
                    ));

    return tokenService.sign(new TokenPayload(user.userId(), user.username()));
  }

  public boolean isLoggedIn(String jwtToken) {
    return tokenService.verify(jwtToken);
  }
}
