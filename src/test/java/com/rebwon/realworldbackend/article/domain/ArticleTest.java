package com.rebwon.realworldbackend.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.rebwon.realworldbackend.member.domain.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArticleTest {
  private Member author;
  private Member member;
  private Article article;

  @BeforeEach
  void setUp() {
    author = Member.register("rebwon", "rebwon@gmail.com", "password");
    member = Member.register("kitty", "kitty@gmail.com", "password");
    article = Article.create("test title", "test desc", "test body", author);
  }

  @Test
  void should_article_unFavorites_could_not_found_member() {
    // Act & Assert
    assertThatThrownBy(
        () -> article.unFavorites(member)
    ).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void should_article_unFavorites_success() {
    // Act
    article.favorites(member);

    // Assert
    assertThat(article.verifyFavorites(member)).isTrue();
    assertThat(article.favoritesCount()).isEqualTo(1);

    // Act
    article.unFavorites(member);

    // Assert
    assertThat(article.verifyFavorites(member)).isFalse();
    assertThat(article.favoritesCount()).isEqualTo(0);
  }

  @Test
  void should_article_favorites_fail_already_favorites() {
    // Act
    article.favorites(member);

    // Assert
    assertThatThrownBy(
        () -> article.favorites(member)
    ).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void should_article_favorites_success() {
    // Act
    article.favorites(member);

    // Assert
    assertThat(article.verifyFavorites(member)).isTrue();
    assertThat(article.favoritesCount()).isEqualTo(1);
  }

  @Test
  void should_verify_author_fail() {
    // Act
    boolean result = article.verifyAuthor(member);

    // Assert
    assertThat(result).isFalse();
  }

  @Test
  void should_verify_author_success() {
    // Act
    boolean result = article.verifyAuthor(author);

    // Assert
    assertThat(result).isTrue();
  }
}