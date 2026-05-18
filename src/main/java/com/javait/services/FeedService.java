package com.javait.services;

import com.javait.models.*;
import com.javait.repository.PostRepository;
import com.javait.repository.SubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FeedService {
  private final SubscriptionRepository subscriptionRepository;
  private final PostRepository postRepository;

  public FeedService(SubscriptionRepository subscriptionRepository,
                     PostRepository postRepository) {
    this.subscriptionRepository = subscriptionRepository;
    this.postRepository = postRepository;
  }

  public Feed getFeedForUser(int userId) {

    List<Subscription> subscriptions =
            subscriptionRepository.findSubscriptionsOf(userId);

    Stream<FeedPost> myPosts =
            postRepository.findByAuthorId(userId)
                    .stream()
                    .map(post -> toFeedPost(post, true));

    List<Integer> authorIds =
            subscriptions.stream().map(Subscription::targetId).toList();

    Stream<FeedPost> subscribedPosts =
            postRepository.findFeedPosts(authorIds)
                    .stream()
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
