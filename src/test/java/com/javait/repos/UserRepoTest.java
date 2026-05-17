package com.javait.repos;

import com.javait.models.Users;
import com.javait.models.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserRepoTest {

  @Test
  void shouldCreateAUser() {
    UserRepo userRepo = new UserRepo();
    User user = userRepo.createUser(1, "Deadpool", "http://Deadpool_url.com");
    assertEquals(1, user.userId());
    assertEquals("Deadpool", user.username());
  }

  @Test
  void shouldFindUserByUsername() {
    UserRepo userRepo = new UserRepo();
    User user = userRepo.createUser(1, "Deadpool", "http://Deadpool_url.com");

    assertEquals(userRepo.findUserByUsername("Deadpool"), Optional.of(user));
  }

  @Test
  void shouldFindUserById() {
    UserRepo userRepo = new UserRepo();
    User user = userRepo.createUser(1, "Deadpool", "http://Deadpool_url.com");

    assertEquals(userRepo.findUserById(1), Optional.of(user));
  }

  @Test
  void shouldReturnAllUsersExcludingRequestingUser() {
    UserRepo userRepo = new UserRepo();
    User requestingUser = userRepo.createUser(1, "Deadpool1", "http" +
            "://Deadpool1_url" +
            ".com");

    userRepo.createUser(2, "Deadpool2", "http://Deadpool2_url" +
            ".com");
    userRepo.createUser(3, "Deadpool3", "http://Deadpool3_url" +
            ".com");

    Users others = userRepo.findOthers(requestingUser.userId());

    assertEquals(2, others.size());
  }

}