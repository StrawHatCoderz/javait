package com.javait.repository.memory;

import com.javait.models.*;
import com.javait.repository.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class InMemoryPostRepoImpl implements PostRepository {
  private final Posts posts;

  public InMemoryPostRepoImpl() {
    this.posts = new Posts();
  }

  @Override
  public Post save(Post post) {
    posts.add(post);
    return post;
  }

  @Override
  public Optional<Post> findById(int postId) {
    return posts.stream()
            .filter(post -> post.postId() == postId)
            .findFirst();
  }

  @Override
  public boolean delete(Post post) {
    return posts.remove(post);
  }

  @Override
  public List<Post> findByAuthorId(int authorId) {
    return posts.stream().filter(post -> post.authorId() == authorId).toList();
  }

  @Override
  public List<Post> findFeedPosts(List<Integer> authorIds) {
    return posts.stream().filter(post -> authorIds.contains(post.authorId())).toList();
  }
}
