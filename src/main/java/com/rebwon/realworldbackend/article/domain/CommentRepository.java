package com.rebwon.realworldbackend.article.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findAllByArticle(Article article);

  Optional<Comment> findByArticleAndId(Article article, Long commentId);
}
