package com.javait.repository.memory;

import com.javait.models.Subscription;
import com.javait.models.Subscriptions;
import com.javait.repository.SubscriptionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InMemorySubscriptionRepoImpl implements SubscriptionRepository {

  private final Subscriptions subscriptions;

  public InMemorySubscriptionRepoImpl() {
    this.subscriptions = new Subscriptions();
  }

  @Override
  public boolean exists(int actorId, int targetId) {
    return subscriptions.isSubscribed(targetId, actorId);
  }

  @Override
  public boolean save(int actorId, int targetId) {
    return subscriptions.add(actorId, targetId);
  }

  @Override
  public boolean delete(int actorId, int targetId) {
    return subscriptions.remove(actorId, targetId);
  }

  @Override
  public List<Subscription> findSubscriptionsOf(int userId) {
    return subscriptions.mySubscriptions(userId);
  }
}
