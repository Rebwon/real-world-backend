package com.rebwon.realworldbackend.member.domain;

import com.rebwon.realworldbackend.common.domain.ChangeHistory;
import java.util.HashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = "email")
public class Member {
  private Long id;
  private String email;
  private String username;
  private String password;
  private ChangeHistory changeHistory;
  private Profile profile;
  private Set<Member> follows = new HashSet<>();

  private Member(Long id, String email, String username, String password, Profile profile) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.password = password;
    this.profile = profile;
  }

  public static Member register(String email, String username, String password) {
    return new Member(null, email, username, password, new Profile("", ""));
  }

  public void changeProfile(String email, String bio, String image) {
    this.email = email;
    this.profile = new Profile(bio, image);
  }

  public void follow(Member target) {
    if(followed(target)) {
      throw new IllegalStateException("Exists follow member");
    }
    this.follows.add(target);
  }

  public void unfollow(Member target) {
    if(!followed(target)) {
      throw new IllegalStateException("Could not found member");
    }
    this.follows.remove(target);
  }

  public boolean followed(Member target) {
    return this.follows.contains(target);
  }

  public int followCount() {
    return this.follows.size();
  }
}
