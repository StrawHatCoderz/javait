package com.javait.repository;

import com.javait.models.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepository {

  Post save(Post post);

  Optional<Post> findById(int postId);

  boolean delete(Post post);

  List<Post> findByAuthorId(int authorId);

  List<Post> findFeedPosts(List<Integer> authorIds);
}