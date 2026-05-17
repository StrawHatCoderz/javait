package com.javait.routes;

import com.javait.exceptions.CookieNotFoundException;
import com.javait.exceptions.TokenNotFoundException;
import com.javait.models.ApiError;
import com.javait.models.ApiResponse;
import com.javait.models.IsLoggedInResponse;
import com.javait.services.AuthService;
import com.javait.services.GithubOAuthService;
import com.javait.utils.CookieParser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;

@RestController
public class AuthController {
  private final AuthService authService;
  private final GithubOAuthService oAuthService;
  private final String redirectUrl;

  public AuthController(AuthService authService,
                        GithubOAuthService oAuthService,
                        @Value("${frontend.url}") String redirectUrl) {
    this.authService = authService;
    this.oAuthService = oAuthService;
    this.redirectUrl = redirectUrl;
  }

  @GetMapping("/api/login")
  public ResponseEntity<Void> redirectToGithubOAuth() {
    String githubRedirectUrl = oAuthService.getGithubRedirectUrl();
    return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(githubRedirectUrl))
            .build();
  }

  @GetMapping("/api/post-login")
  public ResponseEntity<Object> handlePostLogin(@RequestParam String code,
                                                HttpServletResponse response) throws IOException, InterruptedException {
    String jwtToken = authService.loginWithGithub(code);

    Cookie jwtCookie = new Cookie("token", jwtToken);
    jwtCookie.setHttpOnly(true);
    jwtCookie.setSecure(true);
    jwtCookie.setPath("/");
    jwtCookie.setMaxAge(60 * 60 * 24);

    response.addCookie(jwtCookie);

    return ResponseEntity.status(HttpStatus.FOUND)
            .location(URI.create(this.redirectUrl))
            .build();
  }

  @GetMapping("/api/isLoggedIn")
  public ResponseEntity<ApiResponse<IsLoggedInResponse>>
  serveIsLoggedIn(HttpServletRequest request) {

    try {
      String jwtToken = CookieParser.parseJwtToken(request.getCookies());
      boolean isLoggedIn = authService.isLoggedIn(jwtToken);
      IsLoggedInResponse response = new IsLoggedInResponse(isLoggedIn);
      return ResponseEntity.ok(ApiResponse.success(response));
    } catch (CookieNotFoundException | TokenNotFoundException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(ApiResponse.error(new ApiError("AUTH", e.getMessage())));
    }

  }
}
