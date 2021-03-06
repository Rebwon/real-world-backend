package com.rebwon.realworldbackend.modules.article.web.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.rebwon.realworldbackend.modules.article.application.command.UpdateArticleCommand;
import lombok.AllArgsConstructor;
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

    public UpdateArticleCommand toCommand() {
        return new UpdateArticleCommand(this.title, this.description, this.body);
    }
}
