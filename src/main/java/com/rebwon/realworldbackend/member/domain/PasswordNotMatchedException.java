package com.rebwon.realworldbackend.member.domain;

import com.rebwon.realworldbackend.common.exception.SystemException;

public class PasswordNotMatchedException extends SystemException {

  public PasswordNotMatchedException() {
    super("password not matched");
  }
}
