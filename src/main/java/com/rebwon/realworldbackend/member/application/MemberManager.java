package com.rebwon.realworldbackend.member.application;

import com.rebwon.realworldbackend.member.domain.Login;
import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.domain.MemberNotFoundException;
import com.rebwon.realworldbackend.member.domain.MemberRepository;
import com.rebwon.realworldbackend.member.domain.PasswordNotMatchedException;
import com.rebwon.realworldbackend.member.domain.Register;
import com.rebwon.realworldbackend.member.web.request.ProfileUpdateRequest;
import com.rebwon.realworldbackend.member.web.request.RegisterRequest;
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
  public Member register(RegisterRequest request) {
    Member member = Member.register(request.getEmail(), request.getUsername(), passwordEncoder.encode(request.getPassword()));
    return memberRepository.save(member);
  }

  @Transactional(readOnly = true)
  public Member find(Member member) {
    return memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
  }

  public Member changeProfile(Member member, ProfileUpdateRequest request) {
    member.changeProfile(request.getUsername(), request.getEmail(), request.getPassword(), request.getBio(), request.getImage());
    return memberRepository.save(member);
  }
}
