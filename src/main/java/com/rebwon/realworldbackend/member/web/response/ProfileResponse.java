package com.rebwon.realworldbackend.member.web.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.rebwon.realworldbackend.member.domain.ProfileMember;
import lombok.Getter;

@Getter
@JsonRootName("profile")
public class ProfileResponse {
  private final String username;
  private final String bio;
  private final String image;
  private final boolean following;

  public ProfileResponse(String username, String bio, String image, boolean following) {
    this.username = username;
    this.bio = bio;
    this.image = image;
    this.following = following;
  }

  public static ProfileResponse of(ProfileMember member) {
    return new ProfileResponse(member.getMember().getUsername(), member.getMember().getBio(),
        member.getMember().getImage(), member.isFollowing());
  }
}
