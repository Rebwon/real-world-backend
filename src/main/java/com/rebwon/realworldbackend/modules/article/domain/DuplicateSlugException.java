package com.rebwon.realworldbackend.modules.article.domain;

import com.rebwon.realworldbackend.modules.common.exception.SystemException;

public class DuplicateSlugException extends SystemException {

  public DuplicateSlugException() {
    super("duplicate slug");
  }
}
