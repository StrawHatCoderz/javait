package com.javait.routes;

import com.javait.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @GetMapping("/api/login")
  public ResponseEntity<Void> redirectToGithubOAuth() {
    System.out.println("request got");
    String redirectUrl = authService.getGithubRedirectUrl();
    return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(redirectUrl))
            .build();
  }
}
