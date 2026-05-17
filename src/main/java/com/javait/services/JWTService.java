package com.javait.services;

import com.javait.models.TokenPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Optional;

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
            .claim("userId", tokenPayload.userId())
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
  }

  @Override
  public Optional<TokenPayload> parse(String token) {

    try {

      Claims claims = Jwts.parser()
              .setSigningKey(key)
              .build()
              .parseClaimsJws(token)
              .getBody();

      String username = claims.getSubject();

      Integer userId = claims.get("userId", Integer.class);

      TokenPayload payload = new TokenPayload(userId, username);

      return Optional.of(payload);

    } catch (JwtException e) {

      return Optional.empty();
    }
  }

  @Override
  public boolean verify(String token) {
    return parse(token).isPresent();
  }
}