package com.rebwon.realworldbackend.modules.member.web;

import com.rebwon.realworldbackend.infra.security.TokenFactory;
import com.rebwon.realworldbackend.modules.member.application.MemberFacadeService;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import com.rebwon.realworldbackend.modules.member.web.request.LoginRequest;
import com.rebwon.realworldbackend.modules.member.web.request.ProfileUpdateRequest;
import com.rebwon.realworldbackend.modules.member.web.request.RegisterRequest;
import com.rebwon.realworldbackend.modules.member.web.response.MemberResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberApi {

  private final MemberFacadeService facadeService;
  private final TokenFactory tokenFactory;

  @PostMapping("/api/users")
  public ResponseEntity<MemberResponse> register(@RequestBody @Valid RegisterRequest request) {
    return ResponseEntity.ok(
        MemberResponse.of(facadeService.register(request.toCommand()))
    );
  }

  @PostMapping("/api/users/login")
  public ResponseEntity<MemberResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
    Member dbMember = facadeService.login(loginRequest.toCommand());
    String token = tokenFactory.create(dbMember.getEmail());
    return ResponseEntity.ok(MemberResponse.of(dbMember, token));
  }

  @GetMapping("/api/user")
  public ResponseEntity<MemberResponse> find(@AuthenticationPrincipal Member member,
      @RequestHeader("Authorization") String authorization) {
    return ResponseEntity.ok(
        MemberResponse.of(facadeService.find(member), getToken(authorization)));
  }

  private String getToken(String authorization) {
    return authorization.split(" ")[1];
  }

  @PutMapping("/api/user")
  public ResponseEntity<MemberResponse> change(@AuthenticationPrincipal Member member,
      @RequestBody @Valid ProfileUpdateRequest request,
      @RequestHeader("Authorization") String authorization) {
    Member dbMember = facadeService.changeProfile(member, request.toCommand());
    return ResponseEntity.ok(MemberResponse.of(dbMember, getToken(authorization)));
  }
}
