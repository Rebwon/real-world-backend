package com.rebwon.realworldbackend.member.domain;

import com.rebwon.realworldbackend.common.exception.SystemException;

public class MemberNotFoundException extends SystemException {

  public MemberNotFoundException() {
    super("member not found");
  }
}
