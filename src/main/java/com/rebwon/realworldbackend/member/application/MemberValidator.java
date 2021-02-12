package com.rebwon.realworldbackend.member.application;

import com.rebwon.realworldbackend.member.domain.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class MemberValidator {
  private final MemberRepository memberRepository;

  public MemberValidator(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  public boolean verifyEmail(String email) {
    return memberRepository.existsByEmail(email);
  }

  public boolean verifyUsername(String username) {
    return memberRepository.existsByUsername(username);
  }
}
