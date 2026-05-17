package com.javait.routes;

import com.javait.context.UserContext;
import com.javait.exceptions.InvalidSubscriptionException;
import com.javait.exceptions.SelfSubscriptionException;
import com.javait.exceptions.UserNotFoundException;
import com.javait.models.ApiError;
import com.javait.models.ApiResponse;
import com.javait.models.SubscriptionEvent;
import com.javait.services.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  public SubscriptionController(SubscriptionService subscriptionService) {
    this.subscriptionService = subscriptionService;
  }

  @PostMapping("/api/user/subscribe")
  public ResponseEntity<ApiResponse<?>> handleSubscription(@RequestBody int targetId) {
    try {
      boolean subscribe = subscriptionService.subscribe(UserContext.getUserId(), targetId);
      return ResponseEntity.ok(ApiResponse.success(new SubscriptionEvent(subscribe)));
    } catch (SelfSubscriptionException | UserNotFoundException |
             InvalidSubscriptionException e) {
      return ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body(ApiResponse.error(
                      new ApiError("SUBSCRIPTION ERROR", e.getMessage()))
              );
    }
  }

  @PostMapping("/api/user/unsubscribe")
  public ResponseEntity<ApiResponse<?>> handleUnsubscription(@RequestBody int targetId) {
    try {
      boolean unsubscribe =
              subscriptionService.unsubscribe(UserContext.getUserId(),
                      targetId);
      return ResponseEntity.ok(ApiResponse.success(new SubscriptionEvent(!unsubscribe)));
    } catch (UserNotFoundException | InvalidSubscriptionException e) {
      return ResponseEntity
              .status(HttpStatus.BAD_REQUEST)
              .body(ApiResponse.error(
                      new ApiError("SUBSCRIPTION ERROR", e.getMessage()))
              );
    }
  }
}
