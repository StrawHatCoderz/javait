package com.javait.repository;

import com.javait.models.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SpringDataSubscriptionRepo
        extends MongoRepository<Subscription, String> {

  boolean existsByActorIdAndTargetId(
          int actorId,
          int targetId
  );

  long deleteByActorIdAndTargetId(
          int actorId,
          int targetId
  );

  List<Subscription> findByActorId(int actorId);
}