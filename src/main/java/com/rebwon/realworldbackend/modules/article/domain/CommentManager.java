package com.rebwon.realworldbackend.modules.article.domain;

import com.rebwon.realworldbackend.modules.article.application.command.AddCommentCommand;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CommentManager {

  private final CommentRepository commentRepository;
  private final ArticleManager articleManager;

  public CommentManager(
      CommentRepository commentRepository,
      ArticleManager articleManager) {
    this.commentRepository = commentRepository;
    this.articleManager = articleManager;
  }

  public Comment addComment(String slug, Member member, AddCommentCommand command) {
    Article article = articleManager.findOne(slug);
    Comment comment = Comment.write(command.getBody(), article, member);
    return commentRepository.save(comment);
  }

  public List<Comment> findComments(String slug) {
    return commentRepository.findAllByArticle(articleManager.findOne(slug));
  }

  public void deleteComment(String slug, Long commentId, Member member) {
    Article article = articleManager.findOne(slug);
    Comment comment = commentRepository.findByArticleAndId(article, commentId)
        .orElseThrow(() -> new CommentNotFoundException("commentId: " + commentId + " has not found"));
    if (!comment.verifyAuthor(member)) {
      throw new WrongAuthorException("You should not access this comment");
    }
    commentRepository.delete(comment);
  }
}
