package com.javait.config;

import com.javait.services.JWTService;
import com.javait.services.TokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class AppConfig {

  @Bean
  public HttpClient httpClient() {
    return HttpClient.newHttpClient();
  }

  @Bean
  public TokenService tokenService(@Value("${secret.key}") String secretKey) {
    return new TokenService(JWTService.create(secretKey));
  }
}
