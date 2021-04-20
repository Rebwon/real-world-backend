package com.rebwon.realworldbackend.modules.article.application;

import com.rebwon.realworldbackend.modules.article.application.command.AddCommentCommand;
import com.rebwon.realworldbackend.modules.article.domain.Comment;
import com.rebwon.realworldbackend.modules.article.domain.CommentManager;
import com.rebwon.realworldbackend.modules.article.web.response.CommentResponse;
import com.rebwon.realworldbackend.modules.member.application.ProfileMember;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import com.rebwon.realworldbackend.modules.member.web.response.ProfileResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentFacadeService {

    private final CommentManager commentManager;

    public CommentFacadeService(
        CommentManager commentManager) {
        this.commentManager = commentManager;
    }

    public CommentResponse addComment(String slug, Member member, AddCommentCommand command) {
        Comment comment = commentManager.addComment(slug, member, command);
        ProfileMember profileMember = new ProfileMember(member);
        return CommentResponse.of(comment, ProfileResponse.of(profileMember));
    }

    public List<CommentResponse> findAll(String slug) {
        return commentManager.findComments(slug)
            .stream()
            .map(comment -> CommentResponse
                .of(comment, ProfileResponse.of(new ProfileMember(comment.getAuthor()))))
            .collect(Collectors.toList());
    }

    public void deleteComment(String slug, Long commentId, Member member) {
        commentManager.deleteComment(slug, commentId, member);
    }
}
