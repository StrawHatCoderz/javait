package com.javait.services;

import com.javait.models.TokenPayload;

public interface TokenProvider {
  String sign(TokenPayload tokenPayload);

  boolean verify(String token);
}
