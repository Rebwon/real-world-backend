package com.rebwon.realworldbackend.member.web;

import com.rebwon.realworldbackend.member.application.FollowManager;
import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.web.response.ProfileResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowApi {
  private final FollowManager followManager;

  public FollowApi(FollowManager followManager) {
    this.followManager = followManager;
  }

  @PostMapping("/api/profiles/{username}/follow")
  public ResponseEntity<ProfileResponse> following(@AuthenticationPrincipal Member member,
      @PathVariable String username) {
    ProfileResponse response = followManager.follow(member, username);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/api/profiles/{username}/follow")
  public ResponseEntity<ProfileResponse> unFollow(@AuthenticationPrincipal Member member,
      @PathVariable String username) {
    ProfileResponse response = followManager.unfollow(member, username);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/api/profiles/{username}")
  public ResponseEntity<ProfileResponse> find(@AuthenticationPrincipal Member member,
      @PathVariable String username) {
    ProfileResponse response = followManager.find(member, username);
    return ResponseEntity.ok(response);
  }
}
