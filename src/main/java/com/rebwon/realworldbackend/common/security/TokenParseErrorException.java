package com.rebwon.realworldbackend.common.security;

import com.rebwon.realworldbackend.common.exception.SystemException;

public class TokenParseErrorException extends SystemException {

  public TokenParseErrorException() {
    super("Token parse error");
  }
}
