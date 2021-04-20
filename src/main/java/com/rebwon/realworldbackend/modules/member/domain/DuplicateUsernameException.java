package com.rebwon.realworldbackend.modules.member.domain;

import com.rebwon.realworldbackend.modules.common.exception.SystemException;

public class DuplicateUsernameException extends SystemException {

    public DuplicateUsernameException(String message) {
        super(message);
    }
}
