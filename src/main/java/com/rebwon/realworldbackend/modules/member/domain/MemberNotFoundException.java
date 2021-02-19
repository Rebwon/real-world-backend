package com.rebwon.realworldbackend.modules.member.domain;

import com.rebwon.realworldbackend.modules.common.exception.SystemException;

public class MemberNotFoundException extends SystemException {

  public MemberNotFoundException() {
    super("member not found");
  }
}
