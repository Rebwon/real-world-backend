package com.rebwon.realworldbackend.modules.member.application.command;

import lombok.Getter;

@Getter
public class ProfileUpdateCommand {
  private final String username;
  private final String email;
  private final String password;
  private final String bio;
  private final String image;

  public ProfileUpdateCommand(String username, String email, String password, String bio,
      String image) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.bio = bio;
    this.image = image;
  }
}
