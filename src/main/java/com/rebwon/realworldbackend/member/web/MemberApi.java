package com.rebwon.realworldbackend.member.web;

import com.rebwon.realworldbackend.common.security.TokenFactory;
import com.rebwon.realworldbackend.member.domain.DuplicateEmailException;
import com.rebwon.realworldbackend.member.domain.DuplicateUsernameException;
import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.application.MemberManager;
import com.rebwon.realworldbackend.member.application.MemberValidator;
import com.rebwon.realworldbackend.member.web.request.LoginRequest;
import com.rebwon.realworldbackend.member.web.request.ProfileUpdateRequest;
import com.rebwon.realworldbackend.member.web.request.RegisterRequest;
import com.rebwon.realworldbackend.member.web.response.MemberResponse;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    if (memberValidator.verifyUsername(request.getUsername())) {
      throw new DuplicateUsernameException("duplicate username");
    }
    if (memberValidator.verifyEmail(request.getEmail())) {
      throw new DuplicateEmailException("duplicate email");
    }

    Member member = manager.register(request);
    return ResponseEntity.ok(MemberResponse.of(member));
  }

  @PostMapping("/api/users/login")
  public ResponseEntity<MemberResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
    Member dbMember = manager.login(loginRequest.getEmail(), loginRequest.getPassword());
    String token = tokenFactory.create(dbMember);
    return ResponseEntity.ok(MemberResponse.of(dbMember, token));
  }

  @GetMapping("/api/user")
  public ResponseEntity<MemberResponse> find(@AuthenticationPrincipal Member member,
      @RequestHeader("Authorization") String authorization) {
    return ResponseEntity.ok(
        MemberResponse.of(manager.find(member), getToken(authorization)));
  }

  private String getToken(String authorization) {
    return authorization.split(" ")[1];
  }

  @PutMapping("/api/user")
  public ResponseEntity<MemberResponse> change(@AuthenticationPrincipal Member member,
      @RequestBody @Valid ProfileUpdateRequest request,
      @RequestHeader("Authorization") String authorization) {
    if (memberValidator.verifyUsername(request.getUsername())) {
      throw new DuplicateUsernameException("duplicate username");
    }
    if (memberValidator.verifyEmail(request.getEmail())) {
      throw new DuplicateEmailException("duplicate email");
    }

    Member dbMember = manager.changeProfile(member, request);
    return ResponseEntity.ok(MemberResponse.of(dbMember, getToken(authorization)));
  }
}
