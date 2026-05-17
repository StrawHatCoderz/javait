package com.javait.routes;

import com.javait.context.UserContext;
import com.javait.exceptions.InvalidPostCreationException;
import com.javait.exceptions.PostNotFoundException;
import com.javait.models.*;
import com.javait.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @PostMapping("/api/post/create")
  public ResponseEntity<ApiResponse<Post>> handleCreatePost(
          @RequestBody CreatePostPayload createPostPayload) {
    try {
      Post newPost = postService
              .create(UserContext.getUserId(),
                      createPostPayload.title(),
                      createPostPayload.content()
              );
      return ResponseEntity.ok(ApiResponse.success(newPost));
    } catch (InvalidPostCreationException e) {
      return ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body(ApiResponse.error(
                      new ApiError("BAD DATA", e.getMessage()))
              );
    }
  }

  @PostMapping("/api/post/delete/{postId}")
  public ResponseEntity<ApiResponse<PostDeletion>> handleDeletePost(
          @PathVariable int postId) {
    boolean deleted = postService.delete(postId, UserContext.getUserId());
    return ResponseEntity.ok(ApiResponse.success(new PostDeletion(deleted)));
  }

  @PostMapping("/api/post/like/{postId}")
  public ResponseEntity<ApiResponse<PostReaction>> handleLikeOnPost(
          @PathVariable int postId) {
    boolean liked = false;
    try {
      liked = postService.toggleLike(postId, UserContext.getUserId());
    } catch (PostNotFoundException e) {
      return ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body(ApiResponse.error(
                      new ApiError("BAD DATA", e.getMessage()))
              );
    }
    return ResponseEntity.ok(ApiResponse.success(new PostReaction(liked)));
  }
}
