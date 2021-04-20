package com.rebwon.realworldbackend.modules.article.application.command;

import lombok.Getter;

@Getter
public class AddCommentCommand {

    private final String body;

    public AddCommentCommand(String body) {
        this.body = body;
    }
}
