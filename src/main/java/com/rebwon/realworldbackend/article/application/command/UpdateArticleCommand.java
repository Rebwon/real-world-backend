package com.rebwon.realworldbackend.article.application.command;

import lombok.Getter;

@Getter
public class UpdateArticleCommand {
  private final String title;
  private final String description;
  private final String body;

  public UpdateArticleCommand(String title, String description, String body) {
    this.title = title;
    this.description = description;
    this.body = body;
  }
}
