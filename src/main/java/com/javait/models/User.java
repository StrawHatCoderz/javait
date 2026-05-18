package com.javait.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public final class User {
  @Id
  private final int userId;

  private final String username;
  private final String avatarUrl;

  public User(int userId, String username, String avatarUrl) {
    this.userId = userId;
    this.username = username;
    this.avatarUrl = avatarUrl;
  }

  public int userId() {
    return userId;
  }

  public String username() {
    return username;
  }

  public String avatarUrl() {
    return avatarUrl;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) return true;
    if (obj == null || obj.getClass() != this.getClass()) return false;
    var that = (User) obj;
    return this.userId == that.userId &&
            Objects.equals(this.username, that.username) &&
            Objects.equals(this.avatarUrl, that.avatarUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, username, avatarUrl);
  }

  @Override
  public String toString() {
    return "User[" +
            "userId=" + userId + ", " +
            "username=" + username + ", " +
            "avatarUrl=" + avatarUrl + ']';
  }

}
