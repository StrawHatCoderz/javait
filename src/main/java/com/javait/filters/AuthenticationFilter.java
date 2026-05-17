package com.javait.filters;

import com.javait.exceptions.CookieNotFoundException;
import com.javait.exceptions.TokenNotFoundException;
import com.javait.services.AuthService;
import com.javait.utils.CookieParser;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationFilter implements Filter {
  private final AuthService authService;

  public AuthenticationFilter(AuthService authService) {
    this.authService = authService;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
                       FilterChain chain) throws IOException,
          ServletException {
    HttpServletRequest httpServletRequest = (HttpServletRequest) request;
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;

    try {
      String token = CookieParser.parseJwtToken(httpServletRequest.getCookies());
      if (!authService.isLoggedIn(token)) {
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized Access");
        return;
      }

      chain.doFilter(request, response);
    } catch (CookieNotFoundException | TokenNotFoundException e) {
      httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }
  }
}
