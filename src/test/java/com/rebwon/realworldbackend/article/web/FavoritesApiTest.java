package com.rebwon.realworldbackend.article.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.rebwon.realworldbackend.IntegrationTests;
import com.rebwon.realworldbackend.article.domain.Article;
import com.rebwon.realworldbackend.article.domain.ArticleRepository;
import com.rebwon.realworldbackend.member.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

class FavoritesApiTest extends IntegrationTests {

  @Autowired
  private ArticleRepository articleRepository;


  @BeforeEach
  protected void setUp() {
    super.setUp();
    Member member = memberRepository.save(Member.register("jason@gmail.com", "jason", "password"));
    Article article = articleRepository.save(Article.create("hibernate sam", "desc", "body", member));
    article.favorites(setupMember);
    articleRepository.save(Article.create("test title", "description", "body", setupMember));
  }

  @AfterEach
  void tearDown() {
    articleRepository.deleteAll();
    memberRepository.deleteAll();
  }

  @Test
  void should_article_favorites_success() throws Exception {
    // Arrange
    String slug = "test-title";

    // Act
    final ResultActions actions = mockMvc.perform(post("/api/articles/" + slug + "/favorite")
        .header(AUTHORIZATION, setUpToken)
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.article.favorited").value(true))
        .andExpect(jsonPath("$.article.favoritesCount").value(1));
  }

  @Test
  void should_article_favorites_fail_already_favorited_member() throws Exception {
    // Arrange
    String slug = "hibernate-sam";

    // Act
    final ResultActions actions = mockMvc.perform(post("/api/articles/" + slug + "/favorite")
        .header(AUTHORIZATION, setUpToken)
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions.andExpect(status().isUnprocessableEntity());
  }

  @Test
  void should_article_unFavorites_success() throws Exception {
    // Arrange
    String slug = "hibernate-sam";

    // Act
    final ResultActions actions = mockMvc.perform(delete("/api/articles/" + slug + "/favorite")
        .header(AUTHORIZATION, setUpToken)
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.article.favorited").value(false))
        .andExpect(jsonPath("$.article.favoritesCount").value(0));
  }

  @Test
  void should_article_unFavorites_fail_member_not_found() throws Exception {
    // Arrange
    String slug = "test-title";

    // Act
    final ResultActions actions = mockMvc.perform(delete("/api/articles/" + slug + "/favorite")
        .header(AUTHORIZATION, setUpToken)
        .contentType(MediaType.APPLICATION_JSON)
    );

    // Assert
    actions.andExpect(status().isUnprocessableEntity());
  }
}