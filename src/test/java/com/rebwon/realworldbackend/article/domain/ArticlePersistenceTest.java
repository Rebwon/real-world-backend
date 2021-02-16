package com.rebwon.realworldbackend.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rebwon.realworldbackend.PersistenceExtension;
import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.domain.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

public class ArticlePersistenceTest extends PersistenceExtension {

  @Autowired
  private ArticleRepository articleRepository;

  @Autowired
  private MemberRepository memberRepository;

  @BeforeEach
  void setUp() {
    Member author = memberRepository.save(Member.register("rebwon@gmail.com", "rebwon", "password"));
    articleRepository.save(Article.create("test title", "test desc", "test body", author));
  }

  @AfterEach
  void tearDown() {
    articleRepository.deleteAll();
    memberRepository.deleteAll();
  }

  @Test
  @Rollback(value = false)
  void should_article_write() {
    Article article = articleRepository.findBySlug_Value("test-title").get();
    article.modify("spring boot", "spring desc", "spring body");

    assertThat(article.getBody()).isEqualTo("spring body");
    assertThat(article.getTitle()).isEqualTo("spring boot");
    assertThat(article.getSlug().value()).isEqualTo("spring-boot");
    assertThat(article.getDescription()).isEqualTo("spring desc");
    assertThat(article.getChangeHistory().getCreatedAt()).isBefore(article.getChangeHistory().getModifiedAt());
  }
}
