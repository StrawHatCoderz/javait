package com.javait.services;

import com.javait.models.*;
import com.javait.repos.PostRepo;
import com.javait.repos.SubscriptionRepo;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FeedService {
  private final SubscriptionRepo subscriptionRepo;
  private final PostRepo postRepo;

  public FeedService(SubscriptionRepo subscriptionRepo, PostRepo postRepo) {
    this.subscriptionRepo = subscriptionRepo;
    this.postRepo = postRepo;
  }

  public Feed getFeedForUser(int userId) {

    Subscriptions subscriptions =
            subscriptionRepo.getAllSubscribedUser(userId);

    Stream<FeedPost> myPosts =
            postRepo.myPosts(userId)
                    .stream()
                    .map(post -> toFeedPost(post, true));

    Stream<FeedPost> subscribedPosts =
            postRepo.getUserFeed(subscriptions)
                    .map(post -> toFeedPost(post, false));

    return Stream.concat(myPosts, subscribedPosts)
            .sorted(Comparator.comparing(FeedPost::postedOn).reversed())
            .collect(Collectors.toCollection(Feed::new));
  }

  private FeedPost toFeedPost(Post post, boolean isOwner) {
    return new FeedPost(
            post.postId(),
            post.authorId(),
            post.title(),
            post.content(),
            post.postedOn(),
            post.likes(),
            isOwner
    );
  }
}
