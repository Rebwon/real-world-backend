package com.rebwon.realworldbackend.article.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rebwon.realworldbackend.IntegrationTests;
import com.rebwon.realworldbackend.article.domain.Article;
import com.rebwon.realworldbackend.article.domain.ArticleRepository;
import com.rebwon.realworldbackend.article.domain.TagRepository;
import com.rebwon.realworldbackend.article.web.request.CreateArticleRequest;
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

  @BeforeEach
  protected void setUp() {
    super.setUp();
    articleRepository.save(Article.create("test title", "description", "body", setupMember));
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
    CreateArticleRequest request = new CreateArticleRequest("", "", "", Collections.emptyList());

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
    CreateArticleRequest request = new CreateArticleRequest("test title", "desc", "body", Collections.emptyList());

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
        .andDo(print())
        .andExpect(status().isOk());
  }
}