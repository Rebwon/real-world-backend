package com.rebwon.realworldbackend.member.domain;

import lombok.Getter;

@Getter
public class ProfileMember {
  private final Member member;
  private final boolean following;

  public ProfileMember(Member member, boolean following) {
    this.member = member;
    this.following = following;
  }
}
