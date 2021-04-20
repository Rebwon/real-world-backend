package com.rebwon.realworldbackend.modules.article.domain;

import com.rebwon.realworldbackend.modules.common.exception.SystemException;

public class CommentNotFoundException extends SystemException {

    public CommentNotFoundException(String message) {
        super(message);
    }
}
