package com.rebwon.realworldbackend.modules.article.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rebwon.realworldbackend.modules.IntegrationTests;
import com.rebwon.realworldbackend.modules.article.domain.Article;
import com.rebwon.realworldbackend.modules.article.domain.ArticleRepository;
import com.rebwon.realworldbackend.modules.article.domain.Comment;
import com.rebwon.realworldbackend.modules.article.domain.CommentRepository;
import com.rebwon.realworldbackend.modules.article.web.request.AddCommentRequest;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class CommentApiTest extends IntegrationTests {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;
    private Comment setUpComment;
    private Comment setUpWrongComment;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        Article article = articleRepository
            .save(Article.create("test title", "description", "body", setupMember));
        setUpComment = commentRepository
            .save(Comment.write("spring boot good", article, setupMember));
        Member member = memberRepository
            .save(Member.register("jason@gmail.com", "jason", "password"));
        setUpWrongComment = commentRepository
            .save(Comment.write("wrong author sample", article, member));
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        articleRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void should_add_comment_fail_empty_request_body() throws Exception {
        // Arrange
        String slug = "test-title";
        AddCommentRequest request = new AddCommentRequest("");

        // Act
        final ResultActions actions = mockMvc.perform(post("/api/articles/" + slug + "/comments")
            .header(AUTHORIZATION, setUpToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        actions.andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_add_comment_success() throws Exception {
        // Arrange
        String slug = "test-title";
        AddCommentRequest request = new AddCommentRequest("sample comment");

        // Act
        final ResultActions actions = mockMvc.perform(post("/api/articles/" + slug + "/comments")
            .header(AUTHORIZATION, setUpToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        actions
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    void should_delete_comment_fail_wrong_author() throws Exception {
        // Arrange
        String slug = "test-title";

        // Act
        final ResultActions actions = mockMvc
            .perform(delete("/api/articles/" + slug + "/comments/" + setUpWrongComment.getId())
                .header(AUTHORIZATION, setUpToken)
                .contentType(MediaType.APPLICATION_JSON)
            );

        // Assert
        actions.andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_delete_comment_success() throws Exception {
        // Arrange
        String slug = "test-title";

        // Act
        final ResultActions actions = mockMvc
            .perform(delete("/api/articles/" + slug + "/comments/" + setUpComment.getId())
                .header(AUTHORIZATION, setUpToken)
                .contentType(MediaType.APPLICATION_JSON)
            );

        // Assert
        actions.andExpect(status().isNoContent());
    }

    @Test
    void should_find_all_comments_success() throws Exception {
        // Arrange
        String slug = "test-title";

        // Act
        final ResultActions actions = mockMvc.perform(get("/api/articles/" + slug + "/comments")
            .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        actions.andExpect(status().isOk());
    }
}