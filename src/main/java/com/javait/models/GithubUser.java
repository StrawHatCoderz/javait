package com.javait.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubUser(
        int id,
        String login,
        String name,
        @JsonProperty("avatar_url") String avatarUrl
) {}