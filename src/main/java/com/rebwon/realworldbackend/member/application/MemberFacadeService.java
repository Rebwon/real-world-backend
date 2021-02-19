package com.rebwon.realworldbackend.member.application;

import com.rebwon.realworldbackend.member.application.command.LoginCommand;
import com.rebwon.realworldbackend.member.application.command.ProfileUpdateCommand;
import com.rebwon.realworldbackend.member.application.command.RegisterCommand;
import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.domain.MemberManager;
import com.rebwon.realworldbackend.member.web.response.MemberResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberFacadeService {
  private final MemberManager manager;

  public Member login(LoginCommand command) {
    return manager.login(command.getEmail(), command.getPassword());
  }

  public MemberResponse register(RegisterCommand command) {
    Member member = manager.register(command.getEmail(), command.getUsername(),
        command.getPassword());
    return MemberResponse.of(member);
  }

  public Member find(Member member) {
    return manager.find(member);
  }

  public Member changeProfile(Member member, ProfileUpdateCommand command) {
    return manager.changeProfile(member, command);
  }
}
