package com.javait.repository;

import com.javait.models.Users;
import com.javait.models.User;
import com.javait.repository.memory.InMemoryUserRepositoryImpl;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryUserRepositoryImplTest {

  @Test
  void shouldCreateAUser() {
    InMemoryUserRepositoryImpl inMemoryUserRepositoryImpl = new InMemoryUserRepositoryImpl();
    User user = inMemoryUserRepositoryImpl
            .save(new User(
                    1,
                    "Deadpool",
                    "http://Deadpool_url.com"
            ));
    assertEquals(1, user.userId());
    assertEquals("Deadpool", user.username());
  }

  @Test
  void shouldFindUserByUsername() {
    InMemoryUserRepositoryImpl inMemoryUserRepositoryImpl = new InMemoryUserRepositoryImpl();
    User user = inMemoryUserRepositoryImpl
            .save(new User(
                    1,
                    "Deadpool",
                    "http://Deadpool_url.com"
            ));
    assertEquals(inMemoryUserRepositoryImpl.findByUsername("Deadpool"), Optional.of(user));
  }

  @Test
  void shouldFindUserById() {
    InMemoryUserRepositoryImpl inMemoryUserRepositoryImpl = new InMemoryUserRepositoryImpl();
    User user = inMemoryUserRepositoryImpl
            .save(new User(
                    1,
                    "Deadpool",
                    "http://Deadpool_url.com"
            ));
    assertEquals(inMemoryUserRepositoryImpl.findById(1), Optional.of(user));
  }

  @Test
  void shouldReturnAllUsersExcludingRequestingUser() {
    InMemoryUserRepositoryImpl inMemoryUserRepositoryImpl = new InMemoryUserRepositoryImpl();
    User requestingUser = inMemoryUserRepositoryImpl
            .save(new User(
                    1,
                    "Deadpool",
                    "http://Deadpool_url.com"
            ));

    inMemoryUserRepositoryImpl.save(new User(2, "Deadpool2", "http" +
            "://Deadpool2_url" +
            ".com"));
    inMemoryUserRepositoryImpl.save(new User(3, "Deadpool3", "http" +
            "://Deadpool3_url" +
            ".com"));

    List<User> others = inMemoryUserRepositoryImpl.findAllExcept(requestingUser.userId());

    assertEquals(2, others.size());
  }

}