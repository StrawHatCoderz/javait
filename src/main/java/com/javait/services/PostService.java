package com.javait.services;

import com.javait.exceptions.InvalidAccessToDeleteException;
import com.javait.exceptions.InvalidPostCreationException;
import com.javait.exceptions.PostNotFoundException;
import com.javait.models.Likes;
import com.javait.models.Post;
import com.javait.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class PostService {
  private final PostRepository postRepository;

  public PostService(PostRepository postRepository) {
    this.postRepository = postRepository;
  }

  public Post create(int authorId, String title, String content) throws InvalidPostCreationException {
    if (title.isEmpty() || content.isEmpty()) {
      throw new InvalidPostCreationException("Invalid Title/Content");
    }

    int postId = (int) System.currentTimeMillis();
    Post newPost = new Post(postId, authorId, title, content,
            new Date(),
            new Likes());

    return postRepository.save(newPost);
  }

  public boolean delete(int postId, int userId) throws PostNotFoundException, InvalidAccessToDeleteException {
    Optional<Post> postToDelete = postRepository.findById(postId);

    if (postToDelete.isEmpty()) {
      throw new PostNotFoundException("Post Not Found");
    }

    Post post = postToDelete.get();

    if (post.authorId() != userId) {
      throw new InvalidAccessToDeleteException();
    }

    return postRepository.delete(post);
  }

  public boolean toggleLike(int postId, int userId) throws PostNotFoundException {
    Post post = postRepository
            .findById(postId)
            .orElseThrow(() -> new PostNotFoundException("Post Not Found"));

    Likes likes = post.likes();

    if (likes.contains(userId)) {
      likes.remove(Integer.valueOf(userId));
      postRepository.save(post);

      return false;
    }

    likes.add(userId);
    postRepository.save(post);

    return true;
  }
}
