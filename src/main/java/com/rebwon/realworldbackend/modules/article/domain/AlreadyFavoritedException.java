package com.rebwon.realworldbackend.modules.article.domain;

import com.rebwon.realworldbackend.modules.common.exception.SystemException;

public class AlreadyFavoritedException extends SystemException {

  public AlreadyFavoritedException(String message) {
    super(message);
  }
}
