package com.rebwon.realworldbackend.modules.article.web;

import com.rebwon.realworldbackend.modules.article.application.ArticleFacadeService;
import com.rebwon.realworldbackend.modules.article.web.response.ArticleResponse;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FavoritesApi {

    private final ArticleFacadeService facadeService;

    public FavoritesApi(
        ArticleFacadeService facadeService) {
        this.facadeService = facadeService;
    }

    @PostMapping("/api/articles/{slug}/favorite")
    public ResponseEntity<ArticleResponse> favoriteArticle(@PathVariable String slug,
        @AuthenticationPrincipal Member member) {
        ArticleResponse response = facadeService.favorite(slug, member);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/articles/{slug}/favorite")
    public ResponseEntity<ArticleResponse> unFavoriteArticle(@PathVariable String slug,
        @AuthenticationPrincipal Member member) {
        ArticleResponse response = facadeService.unFavorite(slug, member);
        return ResponseEntity.ok(response);
    }
}
