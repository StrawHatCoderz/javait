package com.javait.services;

import com.javait.models.GithubToken;
import com.javait.models.GithubUser;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GithubOAuthServiceTest {

  @Test
  void shouldReturnTokenOnLogin() {
    HttpClient httpClient = HttpClient.newHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();
    String clientId = "sample";
    String clientSecret = "sample-secret";

    GithubOAuthService githubOAuthService =
            new GithubOAuthService(clientId, clientSecret, httpClient, objectMapper);
    assertEquals("https://github.com/login/oauth/authorize?client_id=sample",
            githubOAuthService.getGithubRedirectUrl());
  }

  @Test
  void shouldReturnUserDetails() throws IOException, InterruptedException {
    ObjectMapper objectMapper = mock(ObjectMapper.class);
    HttpClient httpClient = mock(HttpClient.class);
    HttpResponse<String> response = mock(HttpResponse.class);

    String clientId = "sample";
    String clientSecret = "sample-secret";
    String code = "sample-code";

    GithubUser githubUser = new GithubUser(1, "", "deadpool", "sampleUrl");

    when(response.statusCode()).thenReturn(200);

    when(response.body()).thenReturn("""
            {
              "access_token":"sample-token"
            }
            """);

    when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

    when(objectMapper.readValue(anyString(), eq(GithubToken.class)))
            .thenReturn(new GithubToken("sample-token", null));

    when(objectMapper.readValue(anyString(), eq(GithubUser.class)))
            .thenReturn(new GithubUser(1, "", "deadpool", "sampleUrl"));

    GithubOAuthService githubOAuthService =
            new GithubOAuthService(clientId, clientSecret, httpClient, objectMapper);
    assertEquals(githubUser, githubOAuthService.fetchUserDetails(code));
  }
}