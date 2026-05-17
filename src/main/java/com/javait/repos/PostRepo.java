package com.javait.repos;

import com.javait.exceptions.PostNotFoundException;
import com.javait.models.Post;
import com.javait.models.Posts;
import com.javait.models.Subscription;
import com.javait.models.Subscriptions;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

  public Posts myPosts(int userId) {
    return posts.myPosts(userId)
            .sorted(Comparator.comparing(Post::postedOn).reversed())
            .collect(Collectors.toCollection(Posts::new));
  }

  public Stream<Post> getUserFeed(Subscriptions subscriptions) {
    List<Integer> ids =
            subscriptions.stream().map(Subscription::targetId).toList();

    return posts.stream().filter(post -> ids.contains(post.authorId()));
  }
}
