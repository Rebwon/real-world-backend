package com.rebwon.realworldbackend.article.domain;

import com.rebwon.realworldbackend.article.application.command.CreateArticleCommand;
import com.rebwon.realworldbackend.article.application.command.UpdateArticleCommand;
import com.rebwon.realworldbackend.member.domain.Member;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ArticleManager {

  private final ArticleRepository articleRepository;
  private final TagRepository tagRepository;

  public ArticleManager(
      ArticleRepository articleRepository,
      TagRepository tagRepository) {
    this.articleRepository = articleRepository;
    this.tagRepository = tagRepository;
  }

  public Article create(Member member, CreateArticleCommand command) {
    if (articleRepository.existsBySlug_Value(Slug.from(command.getTitle()).value())) {
      throw new DuplicateSlugException();
    }

    Article article = Article
        .create(command.getTitle(), command.getDescription(), command.getBody(), member);
    for (String tagName : command.getTagList()) {
      Tag tag = Optional.ofNullable(tagRepository.findByName(tagName))
          .orElseGet(() -> tagRepository.save(new Tag(tagName)));
      article.addTag(tag);
    }
    return articleRepository.save(article);
  }

  public Article findOne(String slug) {
    return articleRepository.findBySlug_Value(slug).orElseThrow(ArticleNotFoundException::new);
  }

  public Article update(String slug, Member member, UpdateArticleCommand command) {
    Article article = findOne(slug);
    if (!article.verifyAuthor(member)) {
      throw new WrongAuthorException(member.getUsername() + " wrong author");
    }
    article.modify(command.getTitle(), command.getDescription(), command.getBody());
    return article;
  }

  public void delete(String slug, Member member) {
    Article article = findOne(slug);
    if (!article.verifyAuthor(member)) {
      throw new WrongAuthorException(member.getUsername() + " wrong author");
    }
    articleRepository.delete(article);
  }
}
