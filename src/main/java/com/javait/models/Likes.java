package com.javait.models;

import java.util.ArrayList;

public class Likes extends ArrayList<Integer> {

  public boolean isAlreadyLiked(int userId) {
    return this.stream()
            .anyMatch(likedUser -> likedUser.equals(userId));
  }

  public boolean removeLikeOfUser(int userId) {
    return super.remove(Integer.valueOf(userId));
  }
}