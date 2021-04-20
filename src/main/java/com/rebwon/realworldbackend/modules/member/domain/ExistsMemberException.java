package com.rebwon.realworldbackend.modules.member.domain;

import com.rebwon.realworldbackend.modules.common.exception.SystemException;

public class ExistsMemberException extends SystemException {

    public ExistsMemberException() {
        super("exists follow member");
    }
}
