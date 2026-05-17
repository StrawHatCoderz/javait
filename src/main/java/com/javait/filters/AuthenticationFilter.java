package com.javait.filters;

import com.javait.context.UserContext;
import com.javait.exceptions.CookieNotFoundException;
import com.javait.exceptions.TokenNotFoundException;
import com.javait.services.AuthService;
import com.javait.utils.CookieParser;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
  private final AuthService authService;

  public AuthenticationFilter(AuthService authService) {
    this.authService = authService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    try {
      String token = CookieParser.parseJwtToken(request.getCookies());
      if (!authService.isLoggedIn(token)) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized Access");
        return;
      }

      UserContext.setUser(extractUserId(token), extractUsername(token));

      filterChain.doFilter(request, response);
    } catch (CookieNotFoundException | TokenNotFoundException e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
  }

  private String extractUsername(String token) {
    return authService.getCurrentSessionUser(token).get().username();
  }

  private int extractUserId(String token) {
    return authService.getCurrentSessionUser(token).get().userId();
  }
}
