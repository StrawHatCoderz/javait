package com.javait.repos;

import com.javait.exceptions.PostNotFoundException;
import com.javait.models.Post;
import com.javait.models.Posts;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

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

  public boolean deletePost(int postId, int authorId) {
    return posts.removeIf(post -> post.postId() == postId && post.authorId() ==
            authorId);
  }

  public boolean toggleLike(int postId, int userId) throws PostNotFoundException {
    Post postToLike = posts.findByPostId(postId);
    Likes postLikes = postToLike.likes();

    if (postLikes.isAlreadyLiked(userId)) {
      return postToLike.likes().removeLikeOfUser(userId);
    }

    return postToLike.likes().add(userId);
  }
}
