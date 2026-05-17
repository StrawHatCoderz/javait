package com.javait.services;

import com.javait.models.GithubUser;
import com.javait.models.TokenPayload;
import com.javait.models.User;

import com.javait.repos.UserRepo;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class AuthService {
  private final UserRepo userRepo;
  private final TokenService tokenService;
  private final GithubOAuthService oAuthService;

  public AuthService(TokenService tokenService, UserRepo userRepo,
                     GithubOAuthService oAuthService) {
    this.userRepo = userRepo;
    this.tokenService = tokenService;
    this.oAuthService = oAuthService;
  }

  public Optional<TokenPayload> getCurrentSessionUser(String token) {
    return tokenService.parse(token);
  }

  public String loginWithGithub(String code) throws IOException, InterruptedException {
    GithubUser githubUser = oAuthService.fetchUserDetails(code);

    Optional<User> existing = userRepo.findUserById(githubUser.id());

    User user = existing.orElseGet(() -> userRepo.createUser(
            githubUser.id(),
            githubUser.name(),
            githubUser.avatarUrl()
    ));

    return tokenService.sign(new TokenPayload(user.userId(), user.username()));
  }

  public boolean isLoggedIn(String jwtToken) {
    return tokenService.verify(jwtToken);
  }
}
