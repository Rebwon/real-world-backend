package com.rebwon.realworldbackend.common.security;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class TokenExtractorTest {

  @Test
  void should_null_extract_return_empty() {
    TokenExtractor extractor = new TokenExtractor(null);

    Optional<String> result = extractor.extract();

    assertThat(result.isEmpty()).isTrue();
  }

  @Test
  void should_empty_string_extract_return_empty() {
    TokenExtractor extractor = new TokenExtractor("");

    Optional<String> result = extractor.extract();

    assertThat(result.isEmpty()).isTrue();
  }

  @Test
  void should_short_string_extract_return_empty() {
    TokenExtractor extractor = new TokenExtractor("dddd");

    Optional<String> result = extractor.extract();

    assertThat(result.isEmpty()).isTrue();
  }

  @Test
  void should_header_extract_return_string() {
    TokenExtractor extractor = new TokenExtractor("Token header");

    Optional<String> result = extractor.extract();

    assertThat(result.isEmpty()).isFalse();
    assertThat(result.get()).isEqualTo("header");
  }
}