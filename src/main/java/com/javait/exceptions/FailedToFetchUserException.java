package com.javait.exceptions;

public class FailedToFetchUserException extends RuntimeException {
  public FailedToFetchUserException(String message) {
    super(message);
  }
}
