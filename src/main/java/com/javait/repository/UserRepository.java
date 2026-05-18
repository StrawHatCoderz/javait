package com.javait.repository;

import com.javait.models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

  User save(User user);

  Optional<User> findById(int userId);

  Optional<User> findByUsername(String username);

  List<User> findAllExcept(int userId);
}