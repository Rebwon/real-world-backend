package com.rebwon.realworldbackend.member.web;

import com.rebwon.realworldbackend.common.security.TokenFactory;
import com.rebwon.realworldbackend.member.domain.DuplicateEmailException;
import com.rebwon.realworldbackend.member.domain.DuplicateUsernameException;
import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.application.MemberManager;
import com.rebwon.realworldbackend.member.application.MemberValidator;
import com.rebwon.realworldbackend.member.web.request.LoginRequest;
import com.rebwon.realworldbackend.member.web.request.RegisterRequest;
import com.rebwon.realworldbackend.member.web.response.MemberResponse;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberApi {
  private final MemberValidator memberValidator;
  private final MemberManager manager;
  private final TokenFactory tokenFactory;

  public MemberApi(MemberValidator memberValidator,
      MemberManager manager, TokenFactory tokenFactory) {
    this.memberValidator = memberValidator;
    this.manager = manager;
    this.tokenFactory = tokenFactory;
  }

  @PostMapping("/api/users")
  public ResponseEntity<MemberResponse> register(@RequestBody @Valid RegisterRequest request) {
    if(memberValidator.verifyUsername(request.getUsername())) {
      throw new DuplicateUsernameException("duplicate username");
    }
    if(memberValidator.verifyEmail(request.getEmail())) {
      throw new DuplicateEmailException("duplicate email");
    }

    Member member = manager.register(request.getUsername(), request.getEmail(), request.getPassword());
    return ResponseEntity.ok(MemberResponse.of(member));
  }

  @PostMapping("/api/users/login")
  public ResponseEntity<MemberResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
    Member dbMember = manager.login(loginRequest.getEmail(), loginRequest.getPassword());
    String token = tokenFactory.create(dbMember);
    return ResponseEntity.ok(MemberResponse.of(dbMember, token));
  }
}
