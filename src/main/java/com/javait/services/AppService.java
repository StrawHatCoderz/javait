package com.javait.services;

import com.javait.models.*;
import com.javait.repository.SubscriptionRepository;
import com.javait.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppService {
  private final UserRepository userRepository;
  private final SubscriptionRepository subscriptionRepository;

  public AppService(UserRepository userRepository,
                    SubscriptionRepository subscriptionRepository) {
    this.userRepository = userRepository;
    this.subscriptionRepository = subscriptionRepository;
  }

  public AppUsers getAppUsers(int userId) {
    List<User> users = userRepository.findAllExcept(userId);
    List<Subscription> subscriptions =
            subscriptionRepository.findSubscriptionsOf(userId);
    HashSet<Integer> subscribedIds = subscriptions.stream()
            .map(Subscription::targetId)
            .collect(Collectors.toCollection(HashSet::new));

    return users.stream().map(user -> new AppUser(user.userId(),
            user.username(),
            user.avatarUrl(), subscribedIds.contains(user.userId()))).collect(Collectors.toCollection(AppUsers::new));
  }
}
