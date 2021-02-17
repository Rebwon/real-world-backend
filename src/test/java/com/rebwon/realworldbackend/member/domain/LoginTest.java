package com.rebwon.realworldbackend.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.rebwon.realworldbackend.member.application.MemberManager;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class LoginTest {

  @Mock
  private MemberRepository memberRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  private Login login;

  @BeforeEach
  void setUp() {
    login = new MemberManager(memberRepository, passwordEncoder);
  }

  @Test
  void should_login_member_not_found() {
    // Assert
    assertThatThrownBy(
        () -> login.login("rebwon@gmail.com", "password")
    ).isInstanceOf(MemberNotFoundException.class);
  }

  @Test
  void should_login_member_password_not_matched() {
    // Arrange
    BDDMockito.given(memberRepository.findByEmail(Mockito.anyString()))
        .willReturn(Optional.of(Member.register("rebwon@gmail.com", "rebwon", "password")));
    BDDMockito.given(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString()))
        .willReturn(false);

    // Act & Assert
    assertThatThrownBy(
        () -> login.login("rebwon@gmail.com", "password")
    ).isInstanceOf(PasswordNotMatchedException.class);
  }

  @Test
  void should_login_success() {
    // Arrange
    BDDMockito.given(memberRepository.findByEmail(Mockito.anyString()))
        .willReturn(Optional.of(Member.register("rebwon@gmail.com", "rebwon", "password")));
    BDDMockito.given(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString()))
        .willReturn(true);

    // Act
    Member member = login.login("rebwon@gmail.com", "password");

    // Assert
    assertThat(member.getUsername()).isEqualTo("rebwon");
    assertThat(member.getEmail()).isEqualTo("rebwon@gmail.com");
  }
}