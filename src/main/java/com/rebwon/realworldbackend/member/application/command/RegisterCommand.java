package com.rebwon.realworldbackend.member.application.command;

import lombok.Getter;

@Getter
public class RegisterCommand {
  private final String username;
  private final String email;
  private final String password;

  public RegisterCommand(String username, String email, String password) {
    this.username = username;
    this.email = email;
    this.password = password;
  }
}
