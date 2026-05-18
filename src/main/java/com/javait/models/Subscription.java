package com.javait.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document
public final class Subscription {
  @Id
  private String subscriptionId;

  private final int actorId;
  private final int targetId;

  public Subscription(int actorId, int targetId) {
    this.actorId = actorId;
    this.targetId = targetId;
  }

  public int actorId() {
    return actorId;
  }

  public int targetId() {
    return targetId;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    Subscription that = (Subscription) o;
    return actorId == that.actorId && targetId == that.targetId && Objects.equals(subscriptionId, that.subscriptionId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(subscriptionId, actorId, targetId);
  }

  @Override
  public String toString() {
    return "Subscription{" +
            "subscriptionId='" + subscriptionId + '\'' +
            ", actorId=" + actorId +
            ", targetId=" + targetId +
            '}';
  }
}
