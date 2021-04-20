package com.rebwon.realworldbackend.modules.member.domain;

import com.rebwon.realworldbackend.modules.common.exception.SystemException;

public class DuplicateEmailException extends SystemException {

    public DuplicateEmailException(String message) {
        super(message);
    }
}
