package com.rebwon.realworldbackend.member.domain;

import com.rebwon.realworldbackend.common.exception.SystemException;

public class DuplicateUsernameException extends SystemException {

  public DuplicateUsernameException(String message) {
    super(message);
  }
}
