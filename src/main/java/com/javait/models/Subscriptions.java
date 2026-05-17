package com.javait.models;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class Subscriptions extends ArrayList<Subscription> {
  public boolean isSubscribed(int targetId, int actorId) {
    return this.stream().anyMatch(subscription ->
            subscription.targetId() == targetId
                    && subscription.actorId() == actorId);

  }

  public boolean add(int actorId, int targetId) {
    return super.add(new Subscription(actorId, targetId));
  }

  public boolean remove(int actorId, int targetId) {
    Optional<Subscription> subscriptionToRemove = this.stream().filter(subscription ->
            subscription.targetId() == targetId
                    && subscription.actorId() == actorId).findFirst();

    return subscriptionToRemove.map(super::remove).orElse(true);
  }

  public Subscriptions mySubscriptions(int actorId) {
    return this.stream()
            .filter(subscription ->
                    subscription.actorId() == actorId)
            .collect(Collectors.toCollection(Subscriptions::new));
  }
}
