package com.rebwon.realworldbackend.member.application;

import com.rebwon.realworldbackend.member.domain.Login;
import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.domain.MemberNotFoundException;
import com.rebwon.realworldbackend.member.domain.MemberRepository;
import com.rebwon.realworldbackend.member.domain.PasswordNotMatchedException;
import com.rebwon.realworldbackend.member.domain.Register;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MemberManager implements Register, Login {
  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  public MemberManager(MemberRepository memberRepository,
      PasswordEncoder passwordEncoder) {
    this.memberRepository = memberRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Member login(String email, String password) {
    Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    if(!passwordEncoder.matches(password, member.getPassword())) {
      throw new PasswordNotMatchedException();
    }
    return member;
  }

  @Override
  public Member register(String username, String email, String password) {
    Member member = Member.register(username, email, passwordEncoder.encode(password));
    return memberRepository.save(member);
  }
}
