package com.rebwon.realworldbackend.modules.article.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class ArticleListResponse {
  @JsonProperty("articles")
  private final List<ArticleResponse> articleResponses;
  @JsonProperty("articlesCount")
  private final int count;

  public ArticleListResponse(
      List<ArticleResponse> articleResponses, int count) {
    this.articleResponses = articleResponses;
    this.count = count;
  }
}
