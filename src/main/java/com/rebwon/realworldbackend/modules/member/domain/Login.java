package com.rebwon.realworldbackend.modules.member.domain;

public interface Login {

  Member login(String email, String password);
}
