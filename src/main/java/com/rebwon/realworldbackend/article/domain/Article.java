package com.rebwon.realworldbackend.article.domain;

import com.rebwon.realworldbackend.common.domain.ChangeHistory;
import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.domain.MemberNotFoundException;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String title;
  @Embedded
  private Slug slug;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private String body;
  @Embedded
  private ChangeHistory changeHistory;
  @ManyToOne(fetch = FetchType.LAZY)
  private Member author;
  @ManyToMany
  private Set<Tag> tags = new HashSet<>();
  @ManyToMany
  @JoinTable(name = "article_favorites",
      joinColumns = @JoinColumn(name = "article_id"),
      inverseJoinColumns = @JoinColumn(name = "member_id")
  )
  private Set<Member> favorites = new HashSet<>();

  private Article(Long id, String title, Slug slug, String description, String body,
      Member author) {
    this.id = id;
    this.title = title;
    this.slug = slug;
    this.description = description;
    this.body = body;
    this.author = author;
    this.changeHistory = new ChangeHistory();
  }

  public static Article create(String title, String description, String body, Member author) {
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
    if (favorited(target)) {
      throw new AlreadyFavoritedException("already favorites this article");
    }
    this.favorites.add(target);
  }

  public void unFavorites(Member target) {
    if (!favorited(target)) {
      throw new MemberNotFoundException();
    }
    this.favorites.remove(target);
  }

  public boolean favorited(Member target) {
    return this.favorites.contains(target);
  }

  public boolean verifyAuthor(Member target) {
    return this.author.equals(target);
  }

  public int favoritesCount() {
    return this.favorites.size();
  }
}
