package com.rebwon.realworldbackend.member.application;

import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.domain.MemberNotFoundException;
import com.rebwon.realworldbackend.member.domain.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowManager {

  private final MemberRepository memberRepository;

  public ProfileMember follow(Member member, String username) {
    Member target = memberRepository.findByUsername(username)
        .orElseThrow(MemberNotFoundException::new);
    member.follow(target);
    return new ProfileMember(target, member.followed(target));
  }

  public ProfileMember unfollow(Member member, String username) {
    Member target = memberRepository.findByUsername(username)
        .orElseThrow(MemberNotFoundException::new);
    member.unfollow(target);
    return new ProfileMember(target, member.followed(target));
  }

  @Transactional(readOnly = true)
  public ProfileMember find(Member member, String username) {
    Member target = memberRepository.findByUsername(username)
        .orElseThrow(MemberNotFoundException::new);
    if (Optional.ofNullable(member).isEmpty()) {
      return new ProfileMember(target);
    }
    return new ProfileMember(target, member.followed(target));
  }
}
