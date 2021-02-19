package com.rebwon.realworldbackend.modules.article.web.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.rebwon.realworldbackend.modules.article.application.command.CreateArticleCommand;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonRootName("article")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleRequest {

  @NotBlank(message = "can't empty title")
  private String title;
  @NotBlank(message = "can't empty description")
  private String description;
  @NotBlank(message = "can't empty body")
  private String body;
  @Size(max = 3, message = "too many tags")
  private List<String> tagList;

  public CreateArticleCommand toCommand() {
    return new CreateArticleCommand(this.title, this.description, this.body, this.tagList);
  }
}

