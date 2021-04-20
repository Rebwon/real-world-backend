package com.rebwon.realworldbackend.modules.article.web.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.rebwon.realworldbackend.modules.article.domain.Comment;
import com.rebwon.realworldbackend.modules.member.web.response.ProfileResponse;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
@JsonRootName("comment")
public class CommentResponse {

    private final Long id;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final String body;
    private final ProfileResponse author;

    private CommentResponse(Long id, LocalDateTime createdAt, LocalDateTime modifiedAt,
        String body, ProfileResponse author) {
        this.id = id;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.body = body;
        this.author = author;
    }

    public static CommentResponse of(Comment comment, ProfileResponse response) {
        return new CommentResponse(comment.getId(), comment.getChangeHistory().getCreatedAt(),
            comment.getChangeHistory().getModifiedAt(), comment.getBody(), response);
    }
}