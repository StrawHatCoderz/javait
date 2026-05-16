package com.javait.exceptions;

public class FailedToFetchUserException extends Throwable {
  public FailedToFetchUserException(String message) {
    super(message);
  }
}
