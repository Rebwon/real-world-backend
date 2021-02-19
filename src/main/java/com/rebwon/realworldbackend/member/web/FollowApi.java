package com.rebwon.realworldbackend.member.web;

import com.rebwon.realworldbackend.member.application.FollowFacadeService;
import com.rebwon.realworldbackend.member.application.ProfileMember;
import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.web.response.ProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FollowApi {

  private final FollowFacadeService facadeService;

  @PostMapping("/api/profiles/{username}/follow")
  public ResponseEntity<ProfileResponse> following(@AuthenticationPrincipal Member member,
      @PathVariable String username) {
    ProfileMember profileMember = facadeService.follow(member, username);
    return ResponseEntity.ok(ProfileResponse.of(profileMember));
  }

  @DeleteMapping("/api/profiles/{username}/follow")
  public ResponseEntity<ProfileResponse> unFollow(@AuthenticationPrincipal Member member,
      @PathVariable String username) {
    ProfileMember profileMember = facadeService.unfollow(member, username);
    return ResponseEntity.ok(ProfileResponse.of(profileMember));
  }

  @GetMapping("/api/profiles/{username}")
  public ResponseEntity<ProfileResponse> find(@AuthenticationPrincipal Member member,
      @PathVariable String username) {
    ProfileMember profileMember = facadeService.find(member, username);
    return ResponseEntity.ok(ProfileResponse.of(profileMember));
  }
}
