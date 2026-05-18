package com.javait.services;

import com.javait.exceptions.InvalidSubscriptionException;
import com.javait.exceptions.SelfSubscriptionException;
import com.javait.exceptions.UserNotFoundException;
import com.javait.repository.SubscriptionRepository;
import com.javait.repository.UserRepository;
import com.javait.repository.memory.InMemoryUserRepositoryImpl;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

  private final UserRepository userRepository;
  private final SubscriptionRepository subscriptionRepository;

  public SubscriptionService(InMemoryUserRepositoryImpl userRepository,
                             SubscriptionRepository subscriptionRepository) {
    this.userRepository = userRepository;
    this.subscriptionRepository = subscriptionRepository;
  }

  public boolean subscribe(int actorId, int targetId) throws SelfSubscriptionException, UserNotFoundException, InvalidSubscriptionException {

    if (actorId == targetId) {
      throw new SelfSubscriptionException("You can't subscribe yourself");
    }

    userRepository.findById(targetId)
            .orElseThrow(
                    () -> new UserNotFoundException("Invalid user to subscribe")
            );

    if (subscriptionRepository.exists(actorId, targetId)) {
      throw new InvalidSubscriptionException("Already subscribed");
    }

    return subscriptionRepository.save(actorId, targetId);
  }

  public boolean unsubscribe(int actorId, int targetId) throws UserNotFoundException, InvalidSubscriptionException {

    userRepository.findById(targetId).orElseThrow(() -> new UserNotFoundException("Invalid user"));

    if (!subscriptionRepository.exists(actorId, targetId)) {
      throw new InvalidSubscriptionException("You are not subscribed");
    }

    return subscriptionRepository.delete(actorId, targetId);
  }
}