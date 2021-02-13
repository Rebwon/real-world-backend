package com.rebwon.realworldbackend.member.web.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.rebwon.realworldbackend.member.domain.Member;
import lombok.Getter;

@Getter
@JsonRootName("profile")
public class ProfileResponse {
  private final String username;
  private final String bio;
  private final String image;
  private final boolean following;

  private ProfileResponse(String username, String bio, String image, boolean following) {
    this.username = username;
    this.bio = bio;
    this.image = image;
    this.following = following;
  }

  public static ProfileResponse of(Member member, Member currentMember) {
    return new ProfileResponse(member.getUsername(), member.getProfile().getBio(),
        member.getProfile().getImage(), currentMember.followed(member));
  }

  public static ProfileResponse of(Member member) {
    return new ProfileResponse(member.getUsername(), member.getProfile().getBio(),
        member.getProfile().getImage(), false);
  }
}
