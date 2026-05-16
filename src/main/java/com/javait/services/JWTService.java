package com.javait.services;

import com.javait.models.TokenPayload;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JWTService implements TokenProvider {

  private final Key key;

  private JWTService(Key key) {
    this.key = key;
  }

  public static JWTService create(String secretKey) {

    Key key = Keys.hmacShaKeyFor(
            secretKey.getBytes(StandardCharsets.UTF_8)
    );

    return new JWTService(key);
  }

  @Override
  public String sign(TokenPayload tokenPayload) {

    return Jwts.builder()
            .setSubject(tokenPayload.username())
            .setId(String.valueOf(tokenPayload.userId()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
  }

  @Override
  public boolean verify(String token) {

    try {
      Jwts.parser()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token);

      return true;

    } catch (JwtException e) {
      return false;
    }
  }
}