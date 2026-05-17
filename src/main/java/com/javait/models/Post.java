package com.javait.models;

public record Post(int postId, int authorId, String title,
                   String content, java.util.Date postedOn,
                   com.javait.repos.Likes likes) {
}
