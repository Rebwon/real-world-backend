package com.rebwon.realworldbackend.article.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  boolean existsBySlug_Value(String slug);

  @EntityGraph("article-all-fetch")
  Optional<Article> findBySlug_Value(String slug);
}
