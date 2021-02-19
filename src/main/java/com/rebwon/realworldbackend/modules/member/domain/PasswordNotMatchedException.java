package com.rebwon.realworldbackend.modules.member.domain;

import com.rebwon.realworldbackend.modules.common.exception.SystemException;

public class PasswordNotMatchedException extends SystemException {

  public PasswordNotMatchedException() {
    super("password not matched");
  }
}
