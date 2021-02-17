package com.rebwon.realworldbackend.article.domain;

import com.rebwon.realworldbackend.common.exception.SystemException;

public class CommentNotFoundException extends SystemException {

  public CommentNotFoundException(String message) {
    super(message);
  }
}
