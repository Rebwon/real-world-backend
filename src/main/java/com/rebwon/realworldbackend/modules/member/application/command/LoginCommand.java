package com.rebwon.realworldbackend.modules.member.application.command;

import lombok.Getter;

@Getter
public class LoginCommand {
  private final String email;
  private final String password;

  public LoginCommand(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
