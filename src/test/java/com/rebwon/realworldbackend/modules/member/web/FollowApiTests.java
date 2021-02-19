package com.rebwon.realworldbackend.modules.member.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rebwon.realworldbackend.modules.IntegrationTests;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class FollowApiTests extends IntegrationTests {

  private Member target;

  @BeforeEach
  protected void setUp() {
    super.setUp();
    target = memberRepository.save(Member.register("kitty@gmail.com", "kitty", "password"));
  }

  @AfterEach
  void tearDown() {
    memberRepository.deleteAll();
  }

  @Test
  void should_follow_fail_exists_member() throws Exception {
    // Arrange
    setupMember.follow(target);
    final String username = "kitty";

    // Act
    final ResultActions actions = mockMvc.perform(post("/api/profiles/" + username + "/follow")
        .contentType(MediaType.APPLICATION_JSON)
        .header(AUTHORIZATION, setUpToken)
    );

    // Assert
    actions.andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_follow_success() throws Exception {
    // Arrange
    final String username = "kitty";

    // Act
    final ResultActions actions = mockMvc.perform(post("/api/profiles/" + username + "/follow")
        .contentType(MediaType.APPLICATION_JSON)
        .header(AUTHORIZATION, setUpToken)
    );

    // Assert
    actions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.profile.following").value(true));
  }

  @Test
  void should_unfollow_member_not_found() throws Exception {
    // Act
    final ResultActions actions = mockMvc.perform(delete("/api/profiles/jason/follow")
        .contentType(MediaType.APPLICATION_JSON)
        .header(AUTHORIZATION, setUpToken)
    );

    // Assert
    actions.andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_unfollow_success() throws Exception {
    // Arrange
    setupMember.follow(target);
    final String username = "kitty";

    // Act
    final ResultActions actions = mockMvc.perform(delete("/api/profiles/" + username + "/follow")
        .contentType(MediaType.APPLICATION_JSON)
        .header(AUTHORIZATION, setUpToken)
    );

    // Assert
    actions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.profile.following").value(false));
  }

  @Test
  void should_find_profile_no_authentication() throws Exception {
    setupMember.follow(target);
    final String username = "kitty";

    // Act
    final ResultActions actions = mockMvc.perform(get("/api/profiles/" + username)
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.profile.following").value(false))
        .andExpect(jsonPath("$.profile.username").value("kitty"));
  }

  @Test
  void should_find_profile_authentication() throws Exception {
    setupMember.follow(target);
    final String username = "kitty";

    // Act
    final ResultActions actions = mockMvc.perform(get("/api/profiles/" + username)
        .contentType(MediaType.APPLICATION_JSON)
        .header(AUTHORIZATION, setUpToken)
    );

    // Assert
    actions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.profile.following").value(true))
        .andExpect(jsonPath("$.profile.username").value("kitty"));
  }
}