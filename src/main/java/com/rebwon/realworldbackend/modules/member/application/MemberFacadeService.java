package com.rebwon.realworldbackend.modules.member.application;

import com.rebwon.realworldbackend.modules.member.application.command.LoginCommand;
import com.rebwon.realworldbackend.modules.member.application.command.ProfileUpdateCommand;
import com.rebwon.realworldbackend.modules.member.application.command.RegisterCommand;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import com.rebwon.realworldbackend.modules.member.domain.MemberManager;
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

  public Member register(RegisterCommand command) {
    return manager.register(command.getEmail(), command.getUsername(),
        command.getPassword());
  }

  public Member find(Member member) {
    return manager.find(member);
  }

  public Member changeProfile(Member member, ProfileUpdateCommand command) {
    return manager.changeProfile(member, command);
  }
}
