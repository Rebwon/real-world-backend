package com.rebwon.realworldbackend.modules.member.application;

import com.rebwon.realworldbackend.modules.member.domain.FollowManager;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowFacadeService {
  private final FollowManager followManager;

  public ProfileMember follow(Member member, String username) {
    return followManager.follow(member, username);
  }

  public ProfileMember unfollow(Member member, String username) {
    return followManager.unfollow(member, username);
  }

  public ProfileMember find(Member member, String username) {
    return followManager.find(member, username);
  }
}
