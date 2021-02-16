package com.rebwon.realworldbackend.article.domain;

import com.rebwon.realworldbackend.common.exception.SystemException;

public class WrongAuthorException extends SystemException {

  public WrongAuthorException(String message) {
    super(message);
  }
}
