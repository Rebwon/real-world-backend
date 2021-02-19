package com.rebwon.realworldbackend.modules.article.domain;

import com.rebwon.realworldbackend.modules.common.exception.SystemException;

public class ArticleNotFoundException extends SystemException {

  public ArticleNotFoundException() {
    super("article not found");
  }
}
