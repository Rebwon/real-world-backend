package com.rebwon.realworldbackend.article.domain;

import com.rebwon.realworldbackend.common.exception.SystemException;

public class ArticleNotFoundException extends SystemException {

  public ArticleNotFoundException() {
    super("article not found");
  }
}
