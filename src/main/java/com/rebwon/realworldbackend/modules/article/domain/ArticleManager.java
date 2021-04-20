package com.rebwon.realworldbackend.modules.article.domain;

import com.rebwon.realworldbackend.modules.article.application.command.CreateArticleCommand;
import com.rebwon.realworldbackend.modules.article.application.command.UpdateArticleCommand;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class ArticleManager {

    private final ArticleRepository articleRepository;
    private final ArticleQueryRepository queryRepository;
    private final TagRepository tagRepository;

    public ArticleManager(
        ArticleRepository articleRepository,
        ArticleQueryRepository queryRepository,
        TagRepository tagRepository) {
        this.articleRepository = articleRepository;
        this.queryRepository = queryRepository;
        this.tagRepository = tagRepository;
    }

    public Article create(Member member, CreateArticleCommand command) {
        if (articleRepository.existsBySlug_Value(Slug.from(command.getTitle()).value())) {
            throw new DuplicateSlugException();
        }

        Article article = Article
            .create(command.getTitle(), command.getDescription(), command.getBody(), member);
        for (String tagName : command.getTagList()) {
            Tag tag = Optional.ofNullable(tagRepository.findByName(tagName))
                .orElseGet(() -> tagRepository.save(new Tag(tagName)));
            article.addTag(tag);
        }
        return articleRepository.save(article);
    }

    public Article findOne(String slug) {
        return articleRepository.findBySlug_Value(slug).orElseThrow(ArticleNotFoundException::new);
    }

    public List<Article> findAll(String tagName, String favorited, String author, int offset,
        int limit) {
        List<Long> idList = queryRepository.findAll(tagName, favorited, author, offset, limit);
        return queryRepository.findAllById(idList);
    }

    public Article update(String slug, Member member, UpdateArticleCommand command) {
        Article article = findOne(slug);
        if (!article.verifyAuthor(member)) {
            throw new WrongAuthorException(member.getUsername() + " wrong author");
        }
        article.modify(command.getTitle(), command.getDescription(), command.getBody());
        return article;
    }

    public void delete(String slug, Member member) {
        Article article = findOne(slug);
        if (!article.verifyAuthor(member)) {
            throw new WrongAuthorException(member.getUsername() + " wrong author");
        }
        articleRepository.delete(article);
    }

    public Article favorite(String slug, Member member) {
        Article article = findOne(slug);
        article.favorites(member);
        return article;
    }

    public Article unFavorite(String slug, Member member) {
        Article article = findOne(slug);
        article.unFavorites(member);
        return article;
    }
}
