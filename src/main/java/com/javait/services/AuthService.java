package com.javait.services;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
  public String getGithubRedirectUrl() {
    String clientId = "exampleclientid";
    return String.format("https://github.com/login/oauth/authorize?%s", clientId);
  }
}
