package com.javait.repos;

import com.javait.models.Subscriptions;

public class SubscriptionRepo {

  private final Subscriptions subscriptions;

  public SubscriptionRepo() {
    this.subscriptions = new Subscriptions();
  }

  public boolean alreadySubscribed(int actorId, int targetId) {
    return subscriptions.isSubscribed(targetId, actorId);
  }

  public boolean subscribe(int actorId, int targetId) {
    return subscriptions.add(actorId, targetId);
  }

  public boolean unSubscribe(int actorId, int targetId) {
    return subscriptions.remove(actorId, targetId);
  }
}
