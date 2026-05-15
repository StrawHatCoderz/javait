package com.javait.models;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class Users extends ArrayList<User> {
  @Override
  public boolean add(User user) {
    return super.add(user);
  }

  public Optional<User> find(String username) {
    return this.stream().filter(user -> user.username().equals(username)).findFirst();

  }

  public Optional<User> find(int userId) {
    return this.stream().filter(user -> user.userId() == userId).findFirst();
  }

  public Users filterBy(int userId) {
    return this.stream().filter(user -> user.userId() != userId).collect(Collectors.toCollection(Users::new));
  }
}
