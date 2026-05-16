package com.javait.services;

import com.javait.models.TokenPayload;

public class TokenService {
  private final TokenProvider tokenProvider;

  public TokenService(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  public String sign(TokenPayload tokenPayload) {
    return tokenProvider.sign(tokenPayload);
  }

  public boolean verify(String token) {
    return tokenProvider.verify(token);
  }
}
