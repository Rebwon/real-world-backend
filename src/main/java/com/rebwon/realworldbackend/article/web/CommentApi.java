package com.rebwon.realworldbackend.article.web;

import com.rebwon.realworldbackend.article.application.CommentFacadeService;
import com.rebwon.realworldbackend.article.web.request.AddCommentRequest;
import com.rebwon.realworldbackend.article.web.response.CommentResponse;
import com.rebwon.realworldbackend.member.domain.Member;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentApi {
  private final CommentFacadeService facadeService;

  public CommentApi(
      CommentFacadeService facadeService) {
    this.facadeService = facadeService;
  }

  @PostMapping("/api/articles/{slug}/comments")
  public ResponseEntity<CommentResponse> addComment(@PathVariable String slug,
      @AuthenticationPrincipal Member member, @RequestBody @Valid AddCommentRequest request) {
    CommentResponse response = facadeService.addComment(slug, member, request.toCommand());
    return ResponseEntity.ok(response);
  }

  @GetMapping("/api/articles/{slug}/comments")
  public ResponseEntity<List<CommentResponse>> getComments(@PathVariable String slug) {
    List<CommentResponse> comments = facadeService.findAll(slug);
    return ResponseEntity.ok(comments);
  }

  @DeleteMapping("/api/articles/{slug}/comments/{id}")
  public ResponseEntity deleteComment(@PathVariable String slug, @PathVariable Long id,
      @AuthenticationPrincipal Member member) {
    facadeService.deleteComment(slug, id, member);
    return ResponseEntity.noContent().build();
  }
}
