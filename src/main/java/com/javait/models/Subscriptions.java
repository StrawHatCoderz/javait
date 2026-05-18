package com.javait.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

  public List<Subscription> mySubscriptions(int actorId) {
    return this.stream()
            .filter(subscription ->
                    subscription.actorId() == actorId)
            .toList();
  }
}
