package com.rebwon.realworldbackend.member.domain;

import com.rebwon.realworldbackend.member.web.request.RegisterRequest;

public interface Register {

  Member register(RegisterRequest request);
}
