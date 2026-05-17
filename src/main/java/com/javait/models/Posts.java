package com.javait.models;

import com.javait.exceptions.PostNotFoundException;

import java.util.ArrayList;
import java.util.Optional;

public class Posts extends ArrayList<Post> {
  public Post findByPostId(int postId) throws PostNotFoundException {
    Optional<Post> postToLike =
            this.stream().filter(post -> post.postId() == postId).findFirst();

    if (postToLike.isEmpty()) {
      throw new PostNotFoundException("post not found");
    }
    return postToLike.get();
  }
}
