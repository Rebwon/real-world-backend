package com.rebwon.realworldbackend.article.application;

import com.rebwon.realworldbackend.article.domain.Article;
import com.rebwon.realworldbackend.article.domain.ArticleNotFoundException;
import com.rebwon.realworldbackend.article.domain.ArticleRepository;
import com.rebwon.realworldbackend.article.domain.Tag;
import com.rebwon.realworldbackend.article.domain.TagRepository;
import com.rebwon.realworldbackend.article.domain.WrongAuthorException;
import com.rebwon.realworldbackend.article.web.request.CreateArticleRequest;
import com.rebwon.realworldbackend.article.web.request.UpdateArticleRequest;
import com.rebwon.realworldbackend.member.domain.Member;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ArticleManager {
  private final ArticleRepository articleRepository;
  private final TagRepository tagRepository;

  public ArticleManager(
      ArticleRepository articleRepository,
      TagRepository tagRepository) {
    this.articleRepository = articleRepository;
    this.tagRepository = tagRepository;
  }

  public Article create(Member member, CreateArticleRequest request) {
    Article article = Article.create(request.getTitle(), request.getDescription(), request.getBody(), member);
    for(String tagName : request.getTagList()) {
      Tag tag = Optional.ofNullable(tagRepository.findByName(tagName))
          .orElseGet(() -> tagRepository.save(new Tag(tagName)));
      article.addTag(tag);
    }
    articleRepository.save(article);
    return article;
  }

  public Article findOne(String slug) {
    return articleRepository.findBySlug_Value(slug).orElseThrow(ArticleNotFoundException::new);
  }

  public Article update(String slug, Member member, UpdateArticleRequest request) {
    Article article = findOne(slug);
    if(!article.verifyAuthor(member)) {
      throw new WrongAuthorException(member.getUsername() + " wrong author");
    }
    article.modify(request.getTitle(), request.getDescription(), request.getBody());
    return article;
  }

  public void delete(String slug, Member member) {
    Article article = findOne(slug);
    if(!article.verifyAuthor(member)) {
      throw new WrongAuthorException(member.getUsername() + " wrong author");
    }
    articleRepository.delete(article);
  }
}
