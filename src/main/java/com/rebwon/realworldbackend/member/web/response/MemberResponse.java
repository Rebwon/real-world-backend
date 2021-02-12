package com.rebwon.realworldbackend.member.web.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.rebwon.realworldbackend.member.domain.Member;
import lombok.Getter;

@Getter
@JsonRootName("user")
public class MemberResponse {
  private final String email;
  private final String token;
  private final String username;
  private final String bio;
  private final String image;

  private MemberResponse(String email, String token, String username, String bio,
      String image) {
    this.email = email;
    this.token = token;
    this.username = username;
    this.bio = bio;
    this.image = image;
  }

  public static MemberResponse of(Member member) {
    return new MemberResponse(member.getEmail(), "", member.getUsername(),
        member.getProfile().getBio(), member.getProfile().getImage());
  }

  public static MemberResponse of(Member member, String token) {
    return new MemberResponse(member.getEmail(), token, member.getUsername(),
        member.getProfile().getBio(), member.getProfile().getImage());
  }
}
