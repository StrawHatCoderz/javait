package com.javait.config;

import com.javait.filters.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
  @Bean
  public FilterRegistrationBean<AuthenticationFilter> authenticationFilterFilterRegistrationBean(
          AuthenticationFilter authenticationFilter) {
    FilterRegistrationBean<AuthenticationFilter> registrationBean =
            new FilterRegistrationBean<>();
    registrationBean.setFilter(authenticationFilter);
    registrationBean.addUrlPatterns("/api/post/*");
    registrationBean.addUrlPatterns("/api/user/*");
    return registrationBean;
  }
}
