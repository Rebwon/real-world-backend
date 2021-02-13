package com.rebwon.realworldbackend.member.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rebwon.realworldbackend.IntegrationTests;
import com.rebwon.realworldbackend.member.domain.Member;
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
    actions
        .andDo(print())
        .andExpect(status().isUnprocessableEntity());
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
        .andDo(print())
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
    actions
        .andDo(print())
        .andExpect(status().isUnprocessableEntity());
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
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.profile.following").value(false));;
  }
}