package com.rebwon.realworldbackend.article.application;

import com.rebwon.realworldbackend.article.application.command.CreateArticleCommand;
import com.rebwon.realworldbackend.article.application.command.UpdateArticleCommand;
import com.rebwon.realworldbackend.article.domain.Article;
import com.rebwon.realworldbackend.article.domain.ArticleManager;
import com.rebwon.realworldbackend.article.domain.Tag;
import com.rebwon.realworldbackend.article.web.response.ArticleResponse;
import com.rebwon.realworldbackend.member.application.ProfileMember;
import com.rebwon.realworldbackend.member.domain.Member;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleFacadeService {

  private final ArticleManager manager;

  public ArticleFacadeService(ArticleManager manager) {
    this.manager = manager;
  }

  public ArticleResponse create(Member member, CreateArticleCommand command) {
    Article article = manager.create(member, command);
    ProfileMember profileMember = new ProfileMember(member);
    return ArticleResponse
        .of(article, toArray(article.getTags()), profileMember);
  }

  private String[] toArray(Set<Tag> tags) {
    return tags.stream().map(Tag::getName).toArray(String[]::new);
  }

  public ArticleResponse findOne(String slug) {
    Article article = manager.findOne(slug);
    ProfileMember profileMember = new ProfileMember(article.getAuthor());
    return ArticleResponse
        .of(article, toArray(article.getTags()), profileMember);
  }

  public ArticleResponse update(String slug, Member member, UpdateArticleCommand command) {
    Article article = manager.update(slug, member, command);
    ProfileMember profileMember = new ProfileMember(article.getAuthor());
    return ArticleResponse
        .of(article, toArray(article.getTags()), profileMember);
  }

  public void delete(String slug, Member member) {
    manager.delete(slug, member);
  }

  public ArticleResponse favorite(String slug, Member member) {
    Article article = manager.favorite(slug, member);
    ProfileMember profileMember = new ProfileMember(member);
    return ArticleResponse
        .of(article, toArray(article.getTags()), profileMember);
  }

  public ArticleResponse unFavorite(String slug, Member member) {
    Article article = manager.unFavorite(slug, member);
    ProfileMember profileMember = new ProfileMember(member);
    return ArticleResponse
        .of(article, toArray(article.getTags()), profileMember);
  }
}
