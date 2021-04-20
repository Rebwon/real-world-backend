package com.rebwon.realworldbackend.modules.article.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rebwon.realworldbackend.modules.IntegrationTests;
import com.rebwon.realworldbackend.modules.article.domain.Article;
import com.rebwon.realworldbackend.modules.article.domain.ArticleRepository;
import com.rebwon.realworldbackend.modules.article.domain.Tag;
import com.rebwon.realworldbackend.modules.article.domain.TagRepository;
import com.rebwon.realworldbackend.modules.article.web.request.CreateArticleRequest;
import com.rebwon.realworldbackend.modules.article.web.request.UpdateArticleRequest;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class ArticleApiTest extends IntegrationTests {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagRepository tagRepository;

    private Article article;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        Member member = memberRepository
            .save(Member.register("jason@gmail.com", "jason", "password"));
        articleRepository.save(Article.create("hibernate sam", "desc", "body", member));
        article = Article.create("test title", "description", "body", setupMember);
        article.addTag(tagRepository.save(new Tag("JPA")));
        article.favorites(member);
        articleRepository.save(article);
    }

    @AfterEach
    void tearDown() {
        articleRepository.deleteAll();
        tagRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    void should_create_fail_empty_title_description_body() throws Exception {
        // Arrange
        CreateArticleRequest request = new CreateArticleRequest("", "", "",
            Collections.emptyList());

        // Act
        final ResultActions actions = mockMvc.perform(post("/api/articles")
            .header(AUTHORIZATION, setUpToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        actions.andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_create_fail_too_many_tags() throws Exception {
        // Arrange
        List<String> tags = List.of("hibernate", "spring", "jpa", "kotlin");
        CreateArticleRequest request = new CreateArticleRequest("hibernate", "desc", "body", tags);

        // Act
        final ResultActions actions = mockMvc.perform(post("/api/articles")
            .header(AUTHORIZATION, setUpToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        actions.andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_create_fail_duplicate_slug() throws Exception {
        // Arrange
        CreateArticleRequest request = new CreateArticleRequest("test title", "desc", "body",
            Collections.emptyList());

        // Act
        final ResultActions actions = mockMvc.perform(post("/api/articles")
            .header(AUTHORIZATION, setUpToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        actions.andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_create_success() throws Exception {
        // Arrange
        String title = "spring boot";
        String description = "test description";
        String body = "test Body";
        List<String> tags = List.of("hibernate", "spring", "jpa");
        CreateArticleRequest request = new CreateArticleRequest(title, description, body, tags);

        // Act
        final ResultActions actions = mockMvc.perform(post("/api/articles")
            .header(AUTHORIZATION, setUpToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
        );

        // Assert
        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.article.title").value("spring boot"))
            .andExpect(jsonPath("$.article.slug").value("spring-boot"))
            .andExpect(jsonPath("$.article.tagList").isArray());
    }

    @Test
    void should_find_one_fail_not_found() throws Exception {
        // Act
        final ResultActions actions = mockMvc.perform(get("/api/articles/wrong-slug")
            .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        actions.andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_find_one_success() throws Exception {
        // Arrange
        String slug = "test-title";

        // Act
        final ResultActions actions = mockMvc.perform(get("/api/articles/" + slug)
            .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        actions.andExpect(status().isOk());
    }

    @Test
    void should_find_all_articles() throws Exception {
        // Act
        final ResultActions actions = mockMvc.perform(get("/api/articles")
            .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.ArticleListResponse.articlesCount").value(2));
    }

    @Test
    void should_find_all_articles_conditional_on_author() throws Exception {
        // Act
        final ResultActions actions = mockMvc.perform(get("/api/articles")
            .param("author", "rebwon")
            .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.ArticleListResponse.articlesCount").value(1));
    }

    @Test
    void should_find_all_articles_conditional_on_favorited() throws Exception {
        // Act
        final ResultActions actions = mockMvc.perform(get("/api/articles")
            .param("favorited", "jason")
            .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.ArticleListResponse.articlesCount").value(1));
        ;
    }

    @Test
    void should_find_all_articles_conditional_on_tag() throws Exception {
        // Act
        final ResultActions actions = mockMvc.perform(get("/api/articles")
            .param("tag", "jpa")
            .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        actions.andExpect(status().isOk());
    }

    @Test
    void should_update_fail_wrong_author() throws Exception {
        // Arrange
        String slug = "hibernate-sam";
        UpdateArticleRequest request = new UpdateArticleRequest("change article", "desc", "body");

        // Act
        final ResultActions actions = mockMvc.perform(put("/api/articles/" + slug)
            .header(AUTHORIZATION, setUpToken)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        actions.andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_update_success() throws Exception {
        // Arrange
        String slug = "test-title";
        UpdateArticleRequest request = new UpdateArticleRequest("change article", "desc", "body");

        // Act
        final ResultActions actions = mockMvc.perform(put("/api/articles/" + slug)
            .header(AUTHORIZATION, setUpToken)
            .content(objectMapper.writeValueAsString(request))
            .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        actions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.article.title").value("change article"))
            .andExpect(jsonPath("$.article.slug").value("change-article"));

        assertThat(article.getChangeHistory().getCreatedAt()).isBefore(
            article.getChangeHistory().getModifiedAt());
    }

    @Test
    void should_delete_fail_wrong_author() throws Exception {
        // Arrange
        String slug = "hibernate-sam";

        // Act
        final ResultActions actions = mockMvc.perform(delete("/api/articles/" + slug)
            .header(AUTHORIZATION, setUpToken)
            .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        actions.andExpect(status().isUnprocessableEntity());
    }

    @Test
    void should_delete_success() throws Exception {
        // Arrange
        String slug = "test-title";

        // Act
        final ResultActions actions = mockMvc.perform(delete("/api/articles/" + slug)
            .header(AUTHORIZATION, setUpToken)
            .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        actions.andExpect(status().isNoContent());
    }
}