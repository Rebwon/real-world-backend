package com.rebwon.realworldbackend.article.application;

import com.rebwon.realworldbackend.article.domain.ArticleRepository;
import org.springframework.stereotype.Component;

@Component
public class ArticleValidator {
  private final ArticleRepository articleRepository;

  public ArticleValidator(
      ArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public boolean verifySlug(String slug) {
    return articleRepository.existsBySlug_Value(slug);
  }
}
