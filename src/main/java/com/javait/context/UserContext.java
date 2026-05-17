package com.javait.context;

public class UserContext {
  private static final ThreadLocal<Integer> userIdHolder= new ThreadLocal<>();
  private static final ThreadLocal<String> usernameHolder = new ThreadLocal<>();

  public static void setUser(int userId, String username) {
    userIdHolder.set(userId);
    usernameHolder.set(username);
  }

  public static int getUserId() {
    return userIdHolder.get();
  }

  public static String getUsername() {
    return usernameHolder.get();
  }

  public static void clear() {
    userIdHolder.remove();
    usernameHolder.remove();
  }
}
