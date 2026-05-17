package com.javait.routes;

import com.javait.context.UserContext;
import com.javait.models.*;
import com.javait.services.AppService;
import com.javait.services.FeedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
  private final FeedService feedService;
  private final AppService appService;

  public AppController(FeedService feedService, AppService appService) {
    this.feedService = feedService;
    this.appService = appService;
  }

  @GetMapping("/api/user")
  public ResponseEntity<ApiResponse<?>> serveAppDataToUser() {

    int userId = UserContext.getUserId();
    String username = UserContext.getUsername();

    AppUsers appUsers = appService.getAppUsers(userId);
    Feed feed = feedService.getFeedForUser(userId);

    return ResponseEntity.ok(ApiResponse.success(new AppData(appUsers, feed,
            new User(userId, username, ""))));
  }

}
