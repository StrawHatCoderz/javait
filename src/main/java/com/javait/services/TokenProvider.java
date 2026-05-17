package com.javait.services;

import com.javait.models.TokenPayload;

import java.util.Optional;

public interface TokenProvider {
  String sign(TokenPayload tokenPayload);

  boolean verify(String token);

  Optional<TokenPayload> parse(String token);
}
