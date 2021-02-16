package com.rebwon.realworldbackend.article.web.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonRootName("article")
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleRequest {
  private String title = "";
  private String description = "";
  private String body = "";
}
