package com.rebwon.realworldbackend.article.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

  Optional<Article> findBySlug_Value(String slug);
}
