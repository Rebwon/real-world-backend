package com.rebwon.realworldbackend.common.security;

import java.util.Optional;

public class TokenExtractor {

  private static final String HEADER_PREFIX = "Token ";
  private final String header;

  public TokenExtractor(String header) {
    this.header = header;
  }

  public Optional<String> extract() {
    if (header == null || header.isBlank()) {
      return Optional.empty();
    } else {
      if (header.length() < HEADER_PREFIX.length()) {
        return Optional.empty();
      } else {
        return Optional.of(header.substring(HEADER_PREFIX.length()));
      }
    }
  }
}
