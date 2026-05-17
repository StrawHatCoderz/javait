package com.javait.repos;

import com.javait.models.Post;
import com.javait.models.Users;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class PostRepo {
  private final Posts posts;

  public PostRepo() {
    this.posts = new Posts();
  }

  public Post createPost(int authorId, String title, String content) {
    int postId = (int) System.currentTimeMillis();
    Post newPost = new Post(postId, authorId, title, content,
            new Date(),
            new Likes());

    posts.add(newPost);

    return newPost;
  }
}
