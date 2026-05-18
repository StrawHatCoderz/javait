package com.javait.repository.mongo;

import com.javait.models.User;
import com.javait.repository.SpringDataUserRepo;
import com.javait.repository.UserRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class MongoUserRepositoryImpl implements UserRepository {
  private final SpringDataUserRepo springDataUserRepo;

  public MongoUserRepositoryImpl(SpringDataUserRepo springDataUserRepo) {
    this.springDataUserRepo = springDataUserRepo;
  }

  @Override
  public User save(User user) {
    return springDataUserRepo.save(user);
  }

  @Override
  public Optional<User> findById(int userId) {
    return springDataUserRepo.findById(userId);
  }

  @Override
  public Optional<User> findByUsername(String username) {
    return springDataUserRepo.findByUsername(username);
  }

  @Override
  public List<User> findAllExcept(int userId) {
    return springDataUserRepo.findByUserIdNot(userId);
  }
}
