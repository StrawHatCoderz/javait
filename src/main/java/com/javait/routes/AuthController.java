package com.javait.routes;

import com.javait.exceptions.FailedToFetchUserException;
import com.javait.exceptions.GithubTokenException;
import com.javait.exceptions.InvalidCodeException;
import com.javait.exceptions.UserNotFoundException;
import com.javait.models.User;
import com.javait.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;

@RestController
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @GetMapping("/api/login")
  public ResponseEntity<Void> redirectToGithubOAuth() {
    String redirectUrl = authService.getGithubRedirectUrl();
    return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(redirectUrl))
            .build();
  }


  @GetMapping("/api/post-login")
  public ResponseEntity<Object> handlePostLogin(@RequestParam String code,
                                                @Value("${frontend.url}") String redirectUrl,
                                                HttpServletResponse response) throws UserNotFoundException, GithubTokenException, FailedToFetchUserException, IOException, InterruptedException, InvalidCodeException {
    String jwtToken = authService.loginWithGithub(code);

    Cookie jwtCookie = new Cookie("token", jwtToken);
    jwtCookie.setHttpOnly(true);
    jwtCookie.setSecure(true);
    jwtCookie.setPath("/");
    jwtCookie.setMaxAge(60 * 60 * 24);

    response.addCookie(jwtCookie);

    return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(redirectUrl))
            .build();
  }
}
