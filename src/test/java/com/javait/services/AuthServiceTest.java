package com.javait.services;

import com.javait.models.GithubUser;
import com.javait.models.TokenPayload;
import com.javait.models.User;
import com.javait.repos.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
  private AuthService authService;
  private TokenService mockedTokenService;
  private GithubOAuthService mockedGithubOAuthService;
  private UserRepo mockedUserRepo;

  @BeforeEach
  void setup() {
    mockedTokenService = mock(TokenService.class);
    mockedUserRepo = mock(UserRepo.class);
    mockedGithubOAuthService =
            mock(GithubOAuthService.class);

    authService = new AuthService(mockedTokenService, mockedUserRepo, mockedGithubOAuthService);
  }

  @Test
  void shouldReturnJwtTokenOnLogin() throws IOException, InterruptedException {

    when(mockedGithubOAuthService.fetchUserDetails("sample-code"))
            .thenReturn(new GithubUser(1,
                    "",
                    "deadpool",
                    "sampleUrl"
            ));

    when(mockedUserRepo.findUserById(1)).thenReturn(Optional.of(new User(1, "deadpool",
            "sampleUrl")));

    when(mockedTokenService.sign(new TokenPayload(1, "deadpool"))).thenReturn("sample-jwt-token");

    assertEquals("sample-jwt-token", authService.loginWithGithub("sample-code"));
  }

  @Test
  void shouldReturnLoginStatus() {
    when(mockedTokenService.verify("sample-jwt-token")).thenReturn(true);
    assertTrue(authService.isLoggedIn("sample-jwt-token"));
  }
}