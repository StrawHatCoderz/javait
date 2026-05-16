package com.javait.repos;

import com.javait.models.Users;
import com.javait.exceptions.UserNotFoundException;
import com.javait.models.User;

public class UserRepo {
  private final Users users;

  public UserRepo(Users users) {
    this.users = users;
  }

  public User createUser(int userId, String username, String avatarUrl) {
    User user = new User(userId, username, avatarUrl);
    this.users.add(user);
    return user;
  }

  public User findUserByUsername(String username){
    return this.users
            .find(username)
            .orElse(null);
  }

  public User findUserById(int userId) {
    return this.users
            .find(userId)
            .orElse(null);
  }

  public Users findOthers(int userId) {
    return this.users.filterBy(userId);
  }
}
