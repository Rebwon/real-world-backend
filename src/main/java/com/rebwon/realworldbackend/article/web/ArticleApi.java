package com.rebwon.realworldbackend.article.web;

import com.rebwon.realworldbackend.article.application.ArticleFacadeService;
import com.rebwon.realworldbackend.article.application.ArticleValidator;
import com.rebwon.realworldbackend.article.domain.DuplicateSlugException;
import com.rebwon.realworldbackend.article.domain.Slug;
import com.rebwon.realworldbackend.article.web.request.CreateArticleRequest;
import com.rebwon.realworldbackend.article.web.request.UpdateArticleRequest;
import com.rebwon.realworldbackend.article.web.response.ArticleResponse;
import com.rebwon.realworldbackend.member.domain.Member;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    ArticleResponse response = facadeService.create(member, request.toCommand());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/api/articles/{slug}")
  public ResponseEntity<ArticleResponse> find(@PathVariable String slug) {
    ArticleResponse response = facadeService.findOne(slug);
    return ResponseEntity.ok(response);
  }

  @PutMapping("/api/articles/{slug}")
  public ResponseEntity<ArticleResponse> update(@PathVariable String slug,
      @AuthenticationPrincipal Member member, @RequestBody @Valid UpdateArticleRequest request) {
    ArticleResponse response = facadeService.update(slug, member, request.toCommand());
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/api/articles/{slug}")
  public ResponseEntity delete(@PathVariable String slug, @AuthenticationPrincipal Member member) {
    facadeService.delete(slug, member);
    return ResponseEntity.noContent().build();
  }
}
