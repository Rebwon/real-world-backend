package com.rebwon.realworldbackend.member.application;

import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.domain.MemberNotFoundException;
import com.rebwon.realworldbackend.member.domain.MemberRepository;
import com.rebwon.realworldbackend.member.web.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowManager {
  private final MemberRepository memberRepository;

  public ProfileResponse follow(Member member, String username) {
    Member target = memberRepository.findByUsername(username)
        .orElseThrow(MemberNotFoundException::new);
    member.follow(target);
    return ProfileResponse.of(target, member);
  }

  public ProfileResponse unfollow(Member member, String username) {
    Member target = memberRepository.findByUsername(username)
        .orElseThrow(MemberNotFoundException::new);
    member.unfollow(target);
    return ProfileResponse.of(target, member);
  }
}
