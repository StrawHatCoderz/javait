package com.javait.models;

public record AppUser(int userId, String username, String avatarUrl,
                      boolean isSubscribed) {
}
