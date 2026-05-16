package com.javait.models;

import org.springframework.http.HttpHeaders;

public class GithubOptions {
  private final String host;
  private final String path;
  private final String method;
  private final HttpHeaders headers;

  public GithubOptions(String host, String path, String method, HttpHeaders headers) {
    this.host = host;
    this.path = path;
    this.method = method;
    this.headers = headers;
  }

  public static GithubOptions of(String host, String path, String method, HttpHeaders headers) {
    return new GithubOptions(host, path, method,headers);
  }
}
