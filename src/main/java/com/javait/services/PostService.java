package com.javait.services;

import com.javait.exceptions.InvalidPostCreationException;
import com.javait.exceptions.PostNotFoundException;
import com.javait.models.Post;
import com.javait.repos.PostRepo;
import org.springframework.stereotype.Service;

@Service
public class PostService {
  private final PostRepo postRepo;

  public PostService(PostRepo postRepo) {
    this.postRepo = postRepo;
  }

  public Post create(int authorId, String title, String content) throws InvalidPostCreationException {
    if (title.isEmpty() || content.isEmpty()) {
      throw new InvalidPostCreationException("Invalid Title/Content");
    }

    return postRepo.createPost(authorId, title, content);
  }

  public boolean delete(int postId, int userId) {
    return postRepo.deletePost(postId, userId);
  }

  public boolean toggleLike(int postId, int userId) throws PostNotFoundException {
    return postRepo.toggleLike(postId, userId);
  }
}
