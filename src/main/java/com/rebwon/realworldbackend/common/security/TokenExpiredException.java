package com.rebwon.realworldbackend.common.security;

import com.rebwon.realworldbackend.common.exception.SystemException;

public class TokenExpiredException extends SystemException {

  public TokenExpiredException() {
    super("Token expired error");
  }
}
