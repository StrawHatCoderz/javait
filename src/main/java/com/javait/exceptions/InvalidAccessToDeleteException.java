package com.javait.exceptions;

public class InvalidAccessToDeleteException extends Exception {
  public InvalidAccessToDeleteException() {
    super("Author Only Can Delete Post");
  }
}
