package com.rebwon.realworldbackend.modules.article.web;

import com.rebwon.realworldbackend.modules.article.application.ArticleFacadeService;
import com.rebwon.realworldbackend.modules.article.web.request.CreateArticleRequest;
import com.rebwon.realworldbackend.modules.article.web.request.UpdateArticleRequest;
import com.rebwon.realworldbackend.modules.article.web.response.ArticleListResponse;
import com.rebwon.realworldbackend.modules.article.web.response.ArticleResponse;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArticleApi {

    private final ArticleFacadeService facadeService;

    public ArticleApi(ArticleFacadeService facadeService) {
        this.facadeService = facadeService;
    }

    @PostMapping("/api/articles")
    public ResponseEntity<ArticleResponse> create(@AuthenticationPrincipal Member member,
        @RequestBody @Valid CreateArticleRequest request) {
        ArticleResponse response = facadeService.create(member, request.toCommand());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/articles/{slug}")
    public ResponseEntity<ArticleResponse> find(@PathVariable String slug) {
        ArticleResponse response = facadeService.findOne(slug);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<ArticleListResponse> getArticles(
        @RequestParam(defaultValue = "") String tag,
        @RequestParam(defaultValue = "") String favorited,
        @RequestParam(defaultValue = "") String author,
        @RequestParam(defaultValue = "0") int offset,
        @RequestParam(defaultValue = "20") int limit) {
        return ResponseEntity.ok(facadeService.findAll(tag, favorited, author, offset, limit));
    }

    @PutMapping("/api/articles/{slug}")
    public ResponseEntity<ArticleResponse> update(@PathVariable String slug,
        @AuthenticationPrincipal Member member, @RequestBody @Valid UpdateArticleRequest request) {
        ArticleResponse response = facadeService.update(slug, member, request.toCommand());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/articles/{slug}")
    public ResponseEntity delete(@PathVariable String slug,
        @AuthenticationPrincipal Member member) {
        facadeService.delete(slug, member);
        return ResponseEntity.noContent().build();
    }
}
