package com.rebwon.realworldbackend.article.domain;

import com.rebwon.realworldbackend.common.domain.ChangeHistory;
import com.rebwon.realworldbackend.member.domain.Member;
import java.util.HashSet;
import java.util.Set;

public class Article {
  private Long id;
  private String title;
  private Slug slug;
  private String description;
  private String body;
  private ChangeHistory changeHistory;
  private Member author;
  private Set<Tag> tags = new HashSet<>();
  private Set<Member> favorites = new HashSet<>();

  private Article(Long id, String title, Slug slug, String description, String body, Member author) {
    this.id = id;
    this.title = title;
    this.slug = slug;
    this.description = description;
    this.body = body;
    this.author = author;
  }

  public static Article write(String title, String description, String body, Member author) {
    return new Article(null, title, Slug.from(title), description, body, author);
  }

  public void modify(String title, String description, String body) {
    this.title = title;
    this.slug = Slug.from(title);
    this.description = description;
    this.body = body;
  }

  public void addTag(Tag tag) {
    this.tags.add(tag);
  }

  public void removeTag(Tag tag) {
    this.tags.remove(tag);
  }

  public void favorites(Member target) {
    if(verifyFavorites(target)) {
      throw new IllegalStateException("already favorites this article");
    }
    this.favorites.add(target);
  }

  public void unFavorites(Member target) {
    if(!verifyFavorites(target)) {
      throw new IllegalStateException("Could not found member");
    }
    this.favorites.remove(target);
  }

  public boolean verifyFavorites(Member target) {
    return this.favorites.contains(target);
  }

  public boolean verifyAuthor(Member target) {
    return this.author.equals(target);
  }

  public int favoritesCount() {
    return this.favorites.size();
  }
}
