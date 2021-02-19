package com.rebwon.realworldbackend.modules.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SlugTest {

  @Test
  void should_replace_title_with_slug_whitespace() {
    Slug slug = Slug.from("SpringBoot    is most         popular frameworks");
    assertThat(slug.value()).isEqualTo("springboot-is-most-popular-frameworks");
  }

  @Test
  void should_replace_title_with_slug_lower_case() {
    Slug slug = Slug.from("SPRINGBOOT IS MOST POPULAR FRAMEWORKS");
    assertThat(slug.value()).isEqualTo("springboot-is-most-popular-frameworks");
  }

  @Test
  void should_replace_title_with_slug_in_number() {
    Slug slug = Slug.from("SpringBoot 2.4.1 change spring profiles add");
    assertThat(slug.value()).isEqualTo("springboot-2-4-1-change-spring-profiles-add");
  }
}