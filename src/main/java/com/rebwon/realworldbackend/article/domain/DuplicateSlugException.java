package com.rebwon.realworldbackend.article.domain;

import com.rebwon.realworldbackend.common.exception.SystemException;

public class DuplicateSlugException extends SystemException {

  public DuplicateSlugException() {
    super("duplicate slug");
  }
}
