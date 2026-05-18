package com.javait.repository;

import com.javait.models.Subscription;
import com.javait.models.Subscriptions;

import java.util.List;

public interface SubscriptionRepository {
  boolean exists(int actorId, int targetId);

  boolean save(int actorId, int targetId);

  boolean delete(int actorId, int targetId);

  List<Subscription> findSubscriptionsOf(int userId);

}