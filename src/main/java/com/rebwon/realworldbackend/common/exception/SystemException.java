package com.rebwon.realworldbackend.common.exception;

public class SystemException extends RuntimeException {

  public SystemException(String message) {
    super(message);
  }

  public SystemException(String message, Throwable cause) {
    super(message, cause);
  }

  public SystemException(Throwable cause) {
    super(cause);
  }
}
