package com.rebwon.realworldbackend.article.domain;

import com.rebwon.realworldbackend.common.exception.SystemException;

public class AlreadyFavoritedException extends SystemException {

  public AlreadyFavoritedException(String message) {
    super(message);
  }
}
