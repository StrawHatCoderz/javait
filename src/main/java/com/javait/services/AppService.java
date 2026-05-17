package com.javait.services;

import com.javait.models.*;
import com.javait.repos.SubscriptionRepo;
import com.javait.repos.UserRepo;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.stream.Collectors;

@Service
public class AppService {
  private final UserRepo userRepo;
  private final SubscriptionRepo subscriptionRepo;

  public AppService(UserRepo userRepo, SubscriptionRepo subscriptionRepo) {
    this.userRepo = userRepo;
    this.subscriptionRepo = subscriptionRepo;
  }

  public AppUsers getAppUsers(int userId) {
    Users users = userRepo.findOthers(userId);
    Subscriptions subscriptions = subscriptionRepo.getAllSubscribedUser(userId);
    HashSet<Integer> subscribedIds = subscriptions.stream()
            .map(Subscription::targetId)
            .collect(Collectors.toCollection(HashSet::new));

    return users.stream().map(user -> new AppUser(user.userId(),
            user.username(),
            user.avatarUrl(), subscribedIds.contains(user.userId()))).collect(Collectors.toCollection(AppUsers::new));
  }
}
