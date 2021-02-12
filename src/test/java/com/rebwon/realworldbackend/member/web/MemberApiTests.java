package com.rebwon.realworldbackend.member.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebwon.realworldbackend.common.security.TokenFactory;
import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.domain.MemberRepository;
import com.rebwon.realworldbackend.member.web.request.LoginRequest;
import com.rebwon.realworldbackend.member.web.request.ProfileUpdateRequest;
import com.rebwon.realworldbackend.member.web.request.RegisterRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class MemberApiTests {
  private static final String AUTHORIZATION = "Authorization";

  @Autowired private ObjectMapper objectMapper;
  @Autowired private MockMvc mockMvc;
  @Autowired private MemberRepository memberRepository;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private TokenFactory tokenFactory;
  private String setUpToken;
  private Member setupMember;

  @BeforeEach
  void setUp() {
    setupMember = memberRepository.save(Member.register("rebwon@gmail.com", "rebwon", passwordEncoder.encode("password")));
    setUpToken = "Token " + tokenFactory.create(setupMember);
  }

  @AfterEach
  void tearDown() {
    memberRepository.deleteAll();
  }

  @Test
  void should_member_register_fail_empty_username() throws Exception {
    // Arrange
    RegisterRequest request = new RegisterRequest("", "rebwon@gmail.com", "password");

    // Act
    final ResultActions actions = mockMvc.perform(post("/api/users")
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
    final ResultActions actions = mockMvc.perform(post("/api/users")
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
    final ResultActions actions = mockMvc.perform(post("/api/users")
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
    final ResultActions actions = mockMvc.perform(post("/api/users")
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
    final ResultActions actions = mockMvc.perform(post("/api/users")
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
    final ResultActions actions = mockMvc.perform(post("/api/users")
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
    final ResultActions actions = mockMvc.perform(post("/api/users")
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
    final ResultActions actions = mockMvc.perform(post("/api/users/login")
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
    final ResultActions actions = mockMvc.perform(post("/api/users/login")
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
    final ResultActions actions = mockMvc.perform(post("/api/users/login")
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
    final ResultActions actions = mockMvc.perform(get("/api/user")
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
    final ResultActions actions = mockMvc.perform(put("/api/user")
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
    final ResultActions actions = mockMvc.perform(put("/api/user")
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
    final ResultActions actions = mockMvc.perform(put("/api/user")
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
