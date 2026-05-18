package com.javait.repository.mongo;

import com.javait.models.*;
import com.javait.repository.SpringDataPostRepo;
import com.javait.repository.PostRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class MongoPostRepositoryImpl implements PostRepository {

  private final SpringDataPostRepo springDataPostRepo;

  public MongoPostRepositoryImpl(SpringDataPostRepo springDataPostRepo) {
    this.springDataPostRepo = springDataPostRepo;
  }

  @Override
  public Post save(Post post) {
    return springDataPostRepo.save(post);
  }

  @Override
  public Optional<Post> findById(int postId) {
    return springDataPostRepo.findById(postId);
  }

  @Override
  public boolean delete(Post post) {
    springDataPostRepo.delete(post);
    return true;
  }

  @Override
  public List<Post> findByAuthorId(int authorId) {
    return springDataPostRepo.findByAuthorId(authorId);
  }

  @Override
  public List<Post> findFeedPosts(List<Integer> authorIds) {
    return springDataPostRepo.findByAuthorIdIn(authorIds);
  }
}
