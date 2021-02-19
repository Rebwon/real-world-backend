package com.rebwon.realworldbackend.modules;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebwon.realworldbackend.infra.security.TokenFactory;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import com.rebwon.realworldbackend.modules.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public abstract class IntegrationTests {
  protected static final String AUTHORIZATION = "Authorization";

  @Autowired protected ObjectMapper objectMapper;
  @Autowired protected MockMvc mockMvc;
  @Autowired protected MemberRepository memberRepository;
  @Autowired private PasswordEncoder passwordEncoder;
  @Autowired private TokenFactory tokenFactory;
  protected String setUpToken;
  protected Member setupMember;

  @BeforeEach
  protected void setUp() {
    setupMember = memberRepository.save(Member.register("rebwon@gmail.com", "rebwon", passwordEncoder.encode("password")));
    setUpToken = "Token " + tokenFactory.create(setupMember.getEmail());
  }
}
