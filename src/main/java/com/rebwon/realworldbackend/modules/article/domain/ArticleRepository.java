package com.rebwon.realworldbackend.modules.article.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  boolean existsBySlug_Value(String slug);

  Optional<Article> findBySlug_Value(String slug);
}
