package com.rebwon.realworldbackend.article.application;

import com.rebwon.realworldbackend.article.domain.Article;
import com.rebwon.realworldbackend.article.domain.Tag;
import com.rebwon.realworldbackend.article.web.request.CreateArticleRequest;
import com.rebwon.realworldbackend.article.web.response.ArticleResponse;
import com.rebwon.realworldbackend.member.application.ProfileMember;
import com.rebwon.realworldbackend.member.domain.Member;
import com.rebwon.realworldbackend.member.web.response.ProfileResponse;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class ArticleFacadeService {
  private final ArticleManager manager;

  public ArticleFacadeService(ArticleManager manager) {
    this.manager = manager;
  }

  public ArticleResponse create(Member member, CreateArticleRequest request) {
    Article article = manager.create(member, request);
    ProfileMember profileMember = new ProfileMember(member, false);
    return ArticleResponse.of(article, toArray(article.getTags()), ProfileResponse.of(profileMember));
  }

  private String[] toArray(Set<Tag> tags) {
    return tags.stream().map(Tag::getName).toArray(String[]::new);
  }
}
