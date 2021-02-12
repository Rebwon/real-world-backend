package com.rebwon.realworldbackend.member.domain;

import com.rebwon.realworldbackend.common.exception.SystemException;

public class ExistsMemberException extends SystemException {

  public ExistsMemberException() {
    super("exists follow member");
  }
}
