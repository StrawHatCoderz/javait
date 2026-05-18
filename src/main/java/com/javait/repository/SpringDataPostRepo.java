package com.javait.repository;

import com.javait.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringDataPostRepo extends MongoRepository<Post, Integer> {

  List<Post> findByAuthorId(int authorId);
  List<Post> findByAuthorIdIn(List<Integer> authorIds);

}