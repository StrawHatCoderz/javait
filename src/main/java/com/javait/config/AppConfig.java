package com.javait.config;

import com.javait.models.Users;
import com.javait.repos.UserRepo;
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
  public UserRepo userRepo() {
    return new UserRepo(new Users());
  }
}
