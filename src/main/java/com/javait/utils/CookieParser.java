package com.javait.utils;

import com.javait.exceptions.CookieNotFoundException;
import com.javait.exceptions.TokenNotFoundException;
import jakarta.servlet.http.Cookie;

public class CookieParser {
  public static String parseJwtToken(Cookie[] cookies) throws CookieNotFoundException, TokenNotFoundException {
    if (cookies == null) {
      throw new CookieNotFoundException("No Cookie Found");
    }

    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("token")) {
        return cookie.getValue();
      }
    }

    throw new TokenNotFoundException("Token Not Found");
  }
}
