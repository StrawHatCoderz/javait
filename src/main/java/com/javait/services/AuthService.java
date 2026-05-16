package com.javait.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;

@Service
public class AuthService {
  private final String clientId;
  private final String clientSecret;
  private final HttpClient httpClient;

  public AuthService(@Value("${github.client-id}") String clientId,
                     @Value("${github.client-secret}") String clientSecret,
                     HttpClient httpClient) {
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.httpClient = httpClient;
  }

  public String getGithubRedirectUrl() {
    return String.format("https://github.com/login/oauth/authorize?client_id" +
            "=%s", clientId);
  }
}
