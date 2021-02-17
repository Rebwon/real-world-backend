package com.rebwon.realworldbackend.article.web.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.rebwon.realworldbackend.article.application.command.AddCommentCommand;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonRootName("comment")
@AllArgsConstructor
public class AddCommentRequest {

  @NotBlank(message = "can't empty body")
  private String body;

  public AddCommentCommand toCommand() {
    return new AddCommentCommand(this.body);
  }
}
