package com.javait.models;

import com.javait.repos.Likes;

import java.util.Date;

public record FeedPost(int postId, int authorId, String title, String content,
                       Date postedOn, Likes likes, boolean canDelete) {
}
