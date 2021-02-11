package com.rebwon.realworldbackend.member.domain;

public interface Register {

  Member register(String username, String email, String password);
}
