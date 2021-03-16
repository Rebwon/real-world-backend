package com.rebwon.realworldbackend.modules.member.web;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebwon.realworldbackend.infra.security.TokenFactory;
import com.rebwon.realworldbackend.modules.member.application.MemberFacadeService;
import com.rebwon.realworldbackend.modules.member.web.request.RegisterRequest;
import org.javaunit.autoparams.AutoSource;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(MemberApi.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class MemberWebMvcTest {

  @MockBean
  MemberFacadeService memberFacadeService;

  @MockBean
  TokenFactory tokenFactory;

  @ParameterizedTest
  @AutoSource(repeat = 100)
  void reproduce(RegisterRequest registerRequest) {
    assertNotNull(registerRequest);
  }
}
