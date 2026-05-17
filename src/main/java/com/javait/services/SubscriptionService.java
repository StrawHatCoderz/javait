package com.javait.services;

import com.javait.exceptions.InvalidSubscriptionException;
import com.javait.exceptions.SelfSubscriptionException;
import com.javait.exceptions.UserNotFoundException;
import com.javait.models.User;
import com.javait.repos.SubscriptionRepo;
import com.javait.repos.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionService {
  private final UserRepo userRepo;
  private final SubscriptionRepo subscriptionRepo;

  public SubscriptionService(UserRepo userRepo, SubscriptionRepo subscriptionRepo) {
    this.userRepo = userRepo;
    this.subscriptionRepo = subscriptionRepo;
  }

  public boolean subscribe(int actorId, int targetId) throws
          SelfSubscriptionException,
          UserNotFoundException,
          InvalidSubscriptionException {

    if (targetId == actorId) {
      throw new SelfSubscriptionException("You can't subscribe yourself");
    }

    Optional<User> target = userRepo.findUserById(targetId);

    if (target.isEmpty()) {
      throw new UserNotFoundException("Invalid User To subscribe");
    }

    if (subscriptionRepo.alreadySubscribed(actorId, targetId)) {
      throw new InvalidSubscriptionException("Already subscribed");
    }

    return subscriptionRepo.subscribe(actorId, targetId);
  }

  public boolean unsubscribe(int actorId, int targetId) throws InvalidSubscriptionException, UserNotFoundException {
    Optional<User> target = userRepo.findUserById(targetId);

    if (target.isEmpty()) {
      throw new UserNotFoundException("Invalid User To unsubscribe");
    }

    if (!subscriptionRepo.alreadySubscribed(actorId, targetId)) {
      throw new InvalidSubscriptionException("You can't unsubscribe");
    }
    return subscriptionRepo.unSubscribe(actorId, targetId);
  }
}
