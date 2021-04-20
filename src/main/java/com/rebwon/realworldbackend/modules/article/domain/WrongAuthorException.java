package com.rebwon.realworldbackend.modules.article.domain;

import com.rebwon.realworldbackend.modules.common.exception.SystemException;

public class WrongAuthorException extends SystemException {

    public WrongAuthorException(String message) {
        super(message);
    }
}
