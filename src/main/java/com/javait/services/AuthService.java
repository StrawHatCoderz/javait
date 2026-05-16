package com.javait.services;

import com.javait.exceptions.FailedToFetchUserException;
import com.javait.exceptions.GithubTokenException;
import com.javait.exceptions.InvalidCodeException;
import com.javait.models.GithubToken;
import com.javait.models.GithubUser;
import com.javait.models.TokenPayload;
import com.javait.models.User;
import com.javait.repos.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class AuthService {
  private final String clientId;
  private final String clientSecret;
  private final HttpClient httpClient;
  private final UserRepo userRepo;
  private final TokenService tokenService;

  public AuthService(@Value("${github.client-id}") String clientId,
                     @Value("${github.client-secret}") String clientSecret,
                     HttpClient httpClient, UserRepo userRepo,
                     TokenService tokenService) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.httpClient = httpClient;
    this.userRepo = userRepo;
    this.tokenService = tokenService;
  }

  public String getGithubRedirectUrl() {
    return String.format("https://github.com/login/oauth/authorize?client_id" +
            "=%s", clientId);
  }

  public String loginWithGithub(String code) throws GithubTokenException,
          IOException, InterruptedException, InvalidCodeException {
    GithubUser githubUser = null;

    try {
      githubUser = this.fetchUserDetails(code);
    } catch (FailedToFetchUserException e) {
      throw new RuntimeException(e);
    }

    User existing = userRepo.findUserById(githubUser.id());
    User user = existing != null
            ? existing
            : userRepo.createUser(githubUser.id(), githubUser.name(), githubUser.avatarUrl());

    return tokenService.sign(new TokenPayload(user.userId(), user.username()));
  }

  private GithubUser fetchUserDetails(String code) throws GithubTokenException, IOException, InterruptedException, InvalidCodeException, FailedToFetchUserException {
    GithubToken token = this.fetchToken(code);

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.github.com/user"))
            .header("Authorization", String.format("Bearer %s", token.access_token()))
            .GET()
            .build();

    HttpResponse<String> response = httpClient.send(
            request,
            HttpResponse.BodyHandlers.ofString()
    );

    if (response.statusCode() != 200) {
      throw new FailedToFetchUserException("Failed to fetch user details");
    }

    ObjectMapper objectMapper = new ObjectMapper();

    return objectMapper.readValue(
            response.body(),
            GithubUser.class
    );
  }

  private GithubToken fetchToken(String code)
          throws InterruptedException,
          InvalidCodeException, GithubTokenException, IOException {

    String body = String.format(
            "client_id=%s&client_secret=%s&code=%s",
            clientId,
            clientSecret,
            code
    );

    HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://github.com/login/oauth/access_token"))
            .header("Accept", "application/json")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

    HttpResponse<String> response = httpClient.send(
            request,
            HttpResponse.BodyHandlers.ofString()
    );

    if (response.statusCode() != 200) {
      throw new InvalidCodeException(
              "Failed to exchange code for token"
      );
    }

    ObjectMapper objectMapper = new ObjectMapper();

    GithubToken token = objectMapper.readValue(
            response.body(),
            GithubToken.class
    );

    if (token.access_token() == null) {
      throw new GithubTokenException(token.error());
    }

    return token;
  }

  public boolean isLoggedIn(String jwtToken) {
    return tokenService.verify(jwtToken);
  }
}
