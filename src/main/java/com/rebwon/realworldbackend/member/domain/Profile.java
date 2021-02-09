package com.rebwon.realworldbackend.member.domain;

public class Profile {
  private final String bio;
  private final String image;

  public Profile(String bio, String image) {
    this.bio = bio;
    this.image = image;
  }
}
