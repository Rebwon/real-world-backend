package com.rebwon.realworldbackend.article.application.command;

import lombok.Getter;

@Getter
public class AddCommentCommand {
  private final String body;

  public AddCommentCommand(String body) {
    this.body = body;
  }
}
