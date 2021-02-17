package com.rebwon.realworldbackend.article.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Slug {

  @Column(name = "slug", nullable = false, unique = true)
  private String value;

  private Slug(String value) {
    this.value = toSlug(value);
  }

  private String toSlug(String rawTitle) {
    return rawTitle.toLowerCase().replaceAll("[\\&|[\\uFE30-\\uFFA0]|\\’|\\”|\\s\\?\\,\\.]+", "-");
  }

  public static Slug from(final String rawTitle) {
    return new Slug(rawTitle);
  }

  public String value() {
    return value;
  }
}
