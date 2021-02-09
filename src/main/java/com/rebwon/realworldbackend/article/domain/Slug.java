package com.rebwon.realworldbackend.article.domain;

public class Slug {
  private final String value;

  private Slug(String value) {
    this.value = toSlug(value);
  }

  private String toSlug(String rawTitle) {
    return rawTitle.toLowerCase().replaceAll("[\\&|[\\uFE30-\\uFFA0]|\\’|\\”|\\s\\?\\,\\.]+", "-");
  }

  public static Slug from(final String rawTitle) {
    return new Slug(rawTitle);
  }

  public String getValue() {
    return value;
  }
}
