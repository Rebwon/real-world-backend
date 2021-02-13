package com.rebwon.realworldbackend.member.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rebwon.realworldbackend.IntegrationTests;
import com.rebwon.realworldbackend.member.web.request.LoginRequest;
import com.rebwon.realworldbackend.member.web.request.ProfileUpdateRequest;
import com.rebwon.realworldbackend.member.web.request.RegisterRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

public class MemberApiTests extends IntegrationTests {
  private static final String REGISTER_API = "/api/users";
  private static final String LOGIN_API = "/api/users/login";
  private static final String USER_API = "/api/user";

  @AfterEach
  void tearDown() {
    memberRepository.deleteAll();
  }

  @Test
  void should_member_register_fail_empty_username() throws Exception {
    // Arrange
    RegisterRequest request = new RegisterRequest("", "rebwon@gmail.com", "password");

    // Act
    final ResultActions actions = mockMvc.perform(post(REGISTER_API)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andDo(print())
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_member_register_fail_empty_email() throws Exception {
    // Arrange
    RegisterRequest request = new RegisterRequest("rebwon", "", "password");

    // Act
    final ResultActions actions = mockMvc.perform(post(REGISTER_API)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andDo(print())
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_member_register_fail_invalid_email() throws Exception {
    // Arrange
    RegisterRequest request = new RegisterRequest("rebwon", "rebwon.com", "password");

    // Act
    final ResultActions actions = mockMvc.perform(post(REGISTER_API)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andDo(print())
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_member_register_fail_length_short_password() throws Exception {
    // Arrange
    RegisterRequest request = new RegisterRequest("rebwon", "rebwon@gmail.com", "pasd");

    // Act
    final ResultActions actions = mockMvc.perform(post(REGISTER_API)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andDo(print())
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_member_register_fail_duplicated_by_username() throws Exception{
    // Arrange
    RegisterRequest request = new RegisterRequest("rebwon", "rebwon@gmail.com", "password");

    // Act
    final ResultActions actions = mockMvc.perform(post(REGISTER_API)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andDo(print())
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_member_register_fail_duplicated_by_email() throws Exception {
    // Arrange
    RegisterRequest request = new RegisterRequest("rebwon", "rebwon@gmail.com", "password");

    // Act
    final ResultActions actions = mockMvc.perform(post(REGISTER_API)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andDo(print())
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_member_register_success() throws Exception{
    // Arrange
    RegisterRequest request = new RegisterRequest("kitty", "kitty@gmail.com", "password");

    // Act
    final ResultActions actions = mockMvc.perform(post(REGISTER_API)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.username").value("kitty"))
        .andExpect(jsonPath("$.user.email").value("kitty@gmail.com"));
  }

  @Test
  void should_member_login_fail_member_not_found() throws Exception{
    // Arrange
    LoginRequest request = new LoginRequest("kitty@gmail.com", "password");

    // Act
    final ResultActions actions = mockMvc.perform(post(LOGIN_API)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andDo(print())
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_member_login_fail_password_not_matched() throws Exception{
    // Arrange
    LoginRequest request = new LoginRequest("rebwon@gmail.com", "wrongpass");

    // Act
    final ResultActions actions = mockMvc.perform(post(LOGIN_API)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andDo(print())
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_member_login_success() throws Exception{
    // Arrange
    LoginRequest request = new LoginRequest("rebwon@gmail.com", "password");

    // Act
    final ResultActions actions = mockMvc.perform(post(LOGIN_API)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.token").isString());
  }

  @Test
  void should_member_find_success() throws Exception {
    // Act
    final ResultActions actions = mockMvc.perform(get(USER_API)
        .header(AUTHORIZATION, setUpToken)
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.user.token").isString());
  }

  @Test
  void should_member_change_fail_duplicated_by_username() throws Exception{
    // Arrange
    ProfileUpdateRequest request = new ProfileUpdateRequest("rebwon", "rebwon@gmail.com", "password", "", "");

    // Act
    final ResultActions actions = mockMvc.perform(put(USER_API)
        .header(AUTHORIZATION, setUpToken)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andDo(print())
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_member_change_fail_duplicated_by_email() throws Exception {
    // Arrange
    ProfileUpdateRequest request = new ProfileUpdateRequest("kitty", "rebwon@gmail.com", "password", "", "");

    // Act
    final ResultActions actions = mockMvc.perform(put(USER_API)
        .header(AUTHORIZATION, setUpToken)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andDo(print())
        .andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_member_change_success() throws Exception {
    // Arrange
    ProfileUpdateRequest request = new ProfileUpdateRequest("kitty", "kitty@gmail.com", "passwords", "sample", "sample");

    // Act
    final ResultActions actions = mockMvc.perform(put(USER_API)
        .header(AUTHORIZATION, setUpToken)
        .content(objectMapper.writeValueAsString(request))
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andDo(print())
        .andExpect(status().isOk());
  }
}
