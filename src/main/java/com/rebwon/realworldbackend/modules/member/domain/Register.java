package com.rebwon.realworldbackend.modules.member.domain;

public interface Register {

  Member register(String email, String username, String password);
}
