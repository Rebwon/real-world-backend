package com.rebwon.realworldbackend.member.domain;

public interface Register {

  Member register(String email, String username, String password);
}
