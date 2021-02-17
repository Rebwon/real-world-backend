package com.rebwon.realworldbackend.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberTest {

  private Member rebwon;
  private Member kitty;

  @BeforeEach
  void setUp() {
    rebwon = Member.register("rebwon@gmail.com", "rebwon", "password");
    kitty = Member.register("kitty@gmail.com", "kitty", "password");
  }

  @Test
  void should_member_follow_fail_exists_member() {
    // Act
    rebwon.follow(kitty);

    // Assert
    assertThatThrownBy(
        () -> rebwon.follow(kitty)
    ).isInstanceOf(ExistsMemberException.class);
  }

  @Test
  void should_member_follow_success() {
    // Act
    rebwon.follow(kitty);

    // Assert
    assertThat(rebwon.followed(kitty)).isTrue();
    assertThat(rebwon.followCount()).isEqualTo(1);
  }

  @Test
  void should_member_unfollow_fail_could_not_found_member() {
    // Act & Assert
    assertThatThrownBy(
        () -> rebwon.unfollow(kitty)
    ).isInstanceOf(MemberNotFoundException.class);
  }

  @Test
  void should_member_unfollow_success() {
    // Act
    rebwon.follow(kitty);

    // Assert
    assertThat(rebwon.followed(kitty)).isTrue();
    assertThat(rebwon.followCount()).isEqualTo(1);

    // Act
    rebwon.unfollow(kitty);

    // Assert
    assertThat(rebwon.followed(kitty)).isFalse();
    assertThat(rebwon.followCount()).isEqualTo(0);
  }
}