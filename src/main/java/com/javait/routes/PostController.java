package com.javait.routes;

import com.javait.models.ApiResponse;
import com.javait.models.CreatePostPayload;
import com.javait.models.Post;
import com.javait.models.PostCreationResponse;
import com.javait.services.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @PostMapping("/api/post/create")
  public ResponseEntity<ApiResponse<PostCreationResponse>> handleCreatePost(@RequestBody CreatePostPayload createPostPayload) {
    Post newPost = postService.create(createPostPayload.title(), createPostPayload.content());

    return ResponseEntity.status(200).build();
  }
}
