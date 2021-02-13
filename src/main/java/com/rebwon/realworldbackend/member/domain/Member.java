package com.rebwon.realworldbackend.member.domain;

import com.rebwon.realworldbackend.common.domain.ChangeHistory;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@NamedEntityGraph(
    name = "member-with-follows",
    attributeNodes = {
        @NamedAttributeNode("follows")
    }
)
@Entity @DynamicUpdate @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = {"id", "email"})
public class Member {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, unique = true)
  private String email;
  @Column(nullable = false, unique = true)
  private String username;
  @Column(nullable = false)
  private String password;
  @Embedded
  private ChangeHistory changeHistory;
  @Embedded
  private Profile profile;
  @ManyToMany
  @JoinTable(name = "member_follows",
      joinColumns = @JoinColumn(name = "follower_id"),
      inverseJoinColumns = @JoinColumn(name = "following_id")
  )
  private Set<Member> follows = new HashSet<>();

  private Member(Long id, String email, String username, String password, Profile profile) {
    this.id = id;
    this.email = email;
    this.username = username;
    this.password = password;
    this.profile = profile;
    this.changeHistory = new ChangeHistory();
  }

  public static Member register(String email, String username, String password) {
    return new Member(null, email, username, password, new Profile("", ""));
  }

  public void changeProfile(String username, String email, String password, String bio, String image) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.profile = new Profile(bio, image);
  }

  public void follow(Member target) {
    if(followed(target)) {
      throw new ExistsMemberException();
    }
    this.follows.add(target);
  }

  public void unfollow(Member target) {
    if(!followed(target)) {
      throw new MemberNotFoundException();
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
