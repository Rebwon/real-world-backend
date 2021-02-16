package com.rebwon.realworldbackend.article.application;

import com.rebwon.realworldbackend.article.domain.Article;
import com.rebwon.realworldbackend.article.domain.Tag;
import com.rebwon.realworldbackend.article.web.request.CreateArticleRequest;
import com.rebwon.realworldbackend.article.web.request.UpdateArticleRequest;
import com.rebwon.realworldbackend.article.web.response.ArticleResponse;
import com.rebwon.realworldbackend.member.application.ProfileMember;
import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.web.response.ProfileResponse;
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

  public ArticleResponse create(Member member, CreateArticleRequest request) {
    Article article = manager.create(member, request);
    ProfileMember profileMember = new ProfileMember(member);
    return ArticleResponse.of(article, toArray(article.getTags()), ProfileResponse.of(profileMember));
  }

  private String[] toArray(Set<Tag> tags) {
    return tags.stream().map(Tag::getName).toArray(String[]::new);
  }

  public ArticleResponse findOne(String slug) {
    Article article = manager.findOne(slug);
    ProfileMember profileMember = new ProfileMember(article.getAuthor());
    return ArticleResponse.of(article, toArray(article.getTags()), ProfileResponse.of(profileMember));
  }

  public ArticleResponse update(String slug, Member member, UpdateArticleRequest request) {
    Article article = manager.update(slug, member, request);
    ProfileMember profileMember = new ProfileMember(article.getAuthor());
    return ArticleResponse.of(article, toArray(article.getTags()), ProfileResponse.of(profileMember));
  }

  public void delete(String slug, Member member) {
    manager.delete(slug, member);
  }
}
