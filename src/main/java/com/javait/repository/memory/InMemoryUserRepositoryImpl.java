package com.javait.repository.memory;

import com.javait.models.Users;
import com.javait.models.User;
import com.javait.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
  private final Users users;

  public InMemoryUserRepositoryImpl() {
    this.users = new Users();
  }

  @Override
  public User save(User user) {
    this.users.add(user);
    return user;
  }

  @Override
  public Optional<User> findById(int userId) {
    return users.find(userId);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return users.find(username);
  }

  @Override
  public List<User> findAllExcept(int userId) {
    return users.filterBy(userId);
  }
}
