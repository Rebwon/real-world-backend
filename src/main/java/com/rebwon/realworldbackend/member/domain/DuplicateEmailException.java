package com.rebwon.realworldbackend.member.domain;

import com.rebwon.realworldbackend.common.exception.SystemException;

public class DuplicateEmailException extends SystemException {

  public DuplicateEmailException(String message) {
    super(message);
  }
}
