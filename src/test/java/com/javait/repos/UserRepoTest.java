package com.javait.repos;

import com.javait.models.Users;
import com.javait.exceptions.UserNotFoundException;
import com.javait.models.User;
import org.junit.jupiter.api.Test;

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
  void shouldFindUserByUsername() throws UserNotFoundException {
    UserRepo userRepo = new UserRepo();
    User user = userRepo.createUser(1, "Deadpool", "http://Deadpool_url.com");

    assertEquals(userRepo.findUserByUsername("Deadpool"), user);
  }

  @Test
  void shouldThrowErrorIfUserNotFoundWithGivenUsername() {
    UserRepo userRepo = new UserRepo();
    assertThrows(UserNotFoundException.class,
            () -> userRepo.findUserByUsername("Deadpool"));
  }

  @Test
  void shouldFindUserById() throws UserNotFoundException {
    UserRepo userRepo = new UserRepo();
    User user = userRepo.createUser(1, "Deadpool", "http://Deadpool_url.com");

    assertEquals(userRepo.findUserById(1), user);
  }

  @Test
  void shouldThrowErrorIfUserNotFoundWithGivenId() {
    UserRepo userRepo = new UserRepo();
    assertThrows(UserNotFoundException.class,
            () -> userRepo.findUserById(1));
  }

  @Test
  void shouldReturnAllUsersExcludingRequestingUser() throws UserNotFoundException {
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