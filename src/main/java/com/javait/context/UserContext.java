package com.javait.context;

public class UserContext {
  private static final ThreadLocal<Integer> userIdHolder= new ThreadLocal<>();

  public static void setUserId(int userId) {
    userIdHolder.set(userId);
  }

  public static int getUserId() {
    return userIdHolder.get();
  }

  public static void clear() {
    userIdHolder.remove();
  }
}
