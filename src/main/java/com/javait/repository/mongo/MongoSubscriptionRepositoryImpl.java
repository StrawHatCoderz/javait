package com.javait.repository.mongo;

import com.javait.models.Subscription;
import com.javait.repository.SpringDataSubscriptionRepo;
import com.javait.repository.SubscriptionRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class MongoSubscriptionRepositoryImpl implements SubscriptionRepository {
  private final SpringDataSubscriptionRepo springDataSubscriptionRepo;

  public MongoSubscriptionRepositoryImpl(SpringDataSubscriptionRepo springDataSubscriptionRepo) {
    this.springDataSubscriptionRepo = springDataSubscriptionRepo;
  }

  @Override
  public boolean exists(int actorId, int targetId) {
    return springDataSubscriptionRepo.existsByActorIdAndTargetId(actorId, targetId);
  }

  @Override
  public boolean save(int actorId, int targetId) {
    Subscription subscription = new Subscription(actorId, targetId);
    springDataSubscriptionRepo.save(subscription);

    return true;
  }

  @Override
  public boolean delete(int actorId, int targetId) {

    return springDataSubscriptionRepo
            .deleteByActorIdAndTargetId(
                    actorId,
                    targetId
            ) > 0;
  }

  @Override
  public List<Subscription> findSubscriptionsOf(int userId) {
    return springDataSubscriptionRepo.findByActorId(userId);
  }
}
