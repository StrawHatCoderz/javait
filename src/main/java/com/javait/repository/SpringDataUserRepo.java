package com.javait.repository;

import com.javait.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataUserRepo
        extends MongoRepository<User, Integer> {

  Optional<User> findByUsername(String username);

  List<User> findByUserIdNot(int userId);
}