package com.rebwon.realworldbackend.member.domain;

import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class Profile {
  private String bio;
  private String image;

  public Profile(String bio, String image) {
    this.bio = bio;
    this.image = image;
  }
}
