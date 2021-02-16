package com.rebwon.realworldbackend.article.web;

import com.rebwon.realworldbackend.article.application.ArticleFacadeService;
import com.rebwon.realworldbackend.article.application.ArticleValidator;
import com.rebwon.realworldbackend.article.domain.DuplicateSlugException;
import com.rebwon.realworldbackend.article.domain.Slug;
import com.rebwon.realworldbackend.article.web.request.CreateArticleRequest;
import com.rebwon.realworldbackend.article.web.response.ArticleResponse;
import com.rebwon.realworldbackend.member.domain.Member;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleApi {
  private final ArticleFacadeService facadeService;
  private final ArticleValidator validator;

  public ArticleApi(ArticleFacadeService facadeService,
      ArticleValidator validator) {
    this.facadeService = facadeService;
    this.validator = validator;
  }

  @PostMapping("/api/articles")
  public ResponseEntity<ArticleResponse> create(@AuthenticationPrincipal Member member,
      @RequestBody @Valid CreateArticleRequest request) {
    if(validator.verifySlug(Slug.from(request.getTitle()).value())) {
      throw new DuplicateSlugException();
    }
    ArticleResponse response = facadeService.create(member, request);
    return ResponseEntity.ok(response);
  }
}
