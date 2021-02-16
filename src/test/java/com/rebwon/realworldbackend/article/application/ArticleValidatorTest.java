package com.rebwon.realworldbackend.article.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.rebwon.realworldbackend.article.domain.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleValidatorTest {
  @Mock private ArticleRepository articleRepository;
  private ArticleValidator validator;

  @BeforeEach
  void setUp() {
    validator = new ArticleValidator(articleRepository);
  }

  @Test
  void should_article_slug_exists() {
    BDDMockito.given(articleRepository.existsBySlug_Value(Mockito.anyString())).willReturn(true);

    boolean result = validator.verifySlug("test-title");

    assertThat(result).isTrue();
  }
}