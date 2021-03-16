package com.rebwon.realworldbackend.modules.member.web;

import com.rebwon.realworldbackend.modules.member.web.request.RegisterRequest;
import org.javaunit.autoparams.AutoSource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

public class Sample {

  @ParameterizedTest
  @AutoSource(repeat = 100)
  void name(RegisterRequest registerRequest) {

  }
}
