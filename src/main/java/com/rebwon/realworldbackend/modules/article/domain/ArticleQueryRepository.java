package com.rebwon.realworldbackend.modules.article.domain;

import static com.rebwon.realworldbackend.modules.article.domain.QArticle.article;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.rebwon.realworldbackend.modules.article.domain.QTag;
import com.rebwon.realworldbackend.modules.member.domain.QMember;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ArticleQueryRepository {
  private final JPAQueryFactory queryFactory;

  public ArticleQueryRepository(JPAQueryFactory queryFactory) {
    this.queryFactory = queryFactory;
  }

  public List<Long> findAll(String tagName, String favoritesName, String authorName, int offset, int limit) {
    return queryFactory.selectDistinct(article.id)
        .from(article)
        .leftJoin(article.tags, QTag.tag)
        .leftJoin(article.author, QMember.member)
        .leftJoin(article.favorites, QMember.member)
        .where(eqTagName(tagName), eqFavoritesName(favoritesName), eqAuthorName(authorName))
        .offset(offset)
        .limit(limit)
        .fetch();
  }

  public List<Article> findAllById(List<Long> idList) {
    return queryFactory.selectFrom(article)
        .leftJoin(article.tags, QTag.tag).fetchJoin()
        .leftJoin(article.author, QMember.member).fetchJoin()
        .leftJoin(article.favorites, QMember.member).fetchJoin()
        .where(article.id.in(idList))
        .orderBy(article.changeHistory.createdAt.desc())
        .fetch();
  }

  private BooleanExpression eqTagName(String tagName) {
    if(tagName.isEmpty() && tagName.isBlank()) {
      return null;
    }
    return QTag.tag.name.eq(tagName);
  }

  private BooleanExpression eqAuthorName(String authorName) {
    if(authorName.isEmpty() && authorName.isBlank()) {
      return null;
    }
    return article.author.username.eq(authorName);
  }

  private BooleanExpression eqFavoritesName(String favoritesName) {
    if(favoritesName.isEmpty() && favoritesName.isBlank()) {
      return null;
    }
    return QMember.member.username.eq(favoritesName);
  }
}
