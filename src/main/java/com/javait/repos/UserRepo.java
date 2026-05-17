package com.javait.repos;

import com.javait.models.Users;
import com.javait.models.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepo {
  private final Users users;

  public UserRepo() {
    this.users = new Users();
  }

  public User createUser(int userId, String username, String avatarUrl) {
    User user = new User(userId, username, avatarUrl);
    this.users.add(user);
    return user;
  }

  public Optional<User> findUserByUsername(String username) {
    return this.users.find(username);
  }

  public Optional<User> findUserById(int userId) {
    return this.users.find(userId);
  }

  public Users findOthers(int userId) {
    return this.users.filterBy(userId);
  }
}
