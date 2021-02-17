package com.rebwon.realworldbackend.member.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.rebwon.realworldbackend.member.application.MemberValidator;
import com.rebwon.realworldbackend.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MemberValidatorTest {

  @Mock
  private MemberRepository memberRepository;
  private MemberValidator validator;

  @BeforeEach
  void setUp() {
    validator = new MemberValidator(memberRepository);
  }

  @Test
  void should_member_duplicate_email() {
    // Arrange
    BDDMockito.given(memberRepository.existsByEmail("rebwon@gmail.com")).willReturn(true);

    // Act
    boolean result = validator.verifyEmail("rebwon@gmail.com");

    // Assert
    assertThat(result).isTrue();
  }

  @Test
  void should_member_duplicate_username() {
    // Arrange
    BDDMockito.given(memberRepository.existsByUsername("rebwon")).willReturn(true);

    // Act
    boolean result = validator.verifyUsername("rebwon");

    // Assert
    assertThat(result).isTrue();
  }
}
