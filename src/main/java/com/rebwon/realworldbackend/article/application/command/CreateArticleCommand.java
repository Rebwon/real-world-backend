package com.rebwon.realworldbackend.article.application.command;

import java.util.List;
import lombok.Getter;

@Getter
public class CreateArticleCommand {
  private final String title;
  private final String description;
  private final String body;
  private final List<String> tagList;

  public CreateArticleCommand(String title, String description, String body,
      List<String> tagList) {
    this.title = title;
    this.description = description;
    this.body = body;
    this.tagList = tagList;
  }
}
