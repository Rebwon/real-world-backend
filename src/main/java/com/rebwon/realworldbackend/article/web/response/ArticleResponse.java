package com.rebwon.realworldbackend.article.web.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.rebwon.realworldbackend.article.domain.Article;
import com.rebwon.realworldbackend.member.application.ProfileMember;
import com.rebwon.realworldbackend.member.web.response.ProfileResponse;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@JsonRootName("article")
public class ArticleResponse {

  private final String slug;
  private final String title;
  private final String description;
  private final String body;
  private final String[] tagList;
  private final LocalDateTime createdAt;
  private final LocalDateTime modifiedAt;
  private final boolean favorited;
  private final int favoritesCount;
  private final ProfileResponse author;

  private ArticleResponse(String slug, String title, String description, String body,
      String[] tagList, LocalDateTime createdAt, LocalDateTime modifiedAt, boolean favorited,
      int favoritesCount, ProfileResponse author) {
    this.slug = slug;
    this.title = title;
    this.description = description;
    this.body = body;
    this.tagList = tagList;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
    this.favorited = favorited;
    this.favoritesCount = favoritesCount;
    this.author = author;
  }

  public static ArticleResponse of(Article article, String[] tagList, ProfileMember member) {
    return new ArticleResponse(article.getSlug().value(), article.getTitle(),
        article.getDescription(), article.getBody(), tagList, article.getChangeHistory()
        .getCreatedAt(), article.getChangeHistory().getModifiedAt(), article.favorited(member.getMember()),
        article.favoritesCount(),
        ProfileResponse.of(member));
  }
}
