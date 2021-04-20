package com.rebwon.realworldbackend.modules.article.application;

import com.rebwon.realworldbackend.modules.article.application.command.CreateArticleCommand;
import com.rebwon.realworldbackend.modules.article.application.command.UpdateArticleCommand;
import com.rebwon.realworldbackend.modules.article.domain.Article;
import com.rebwon.realworldbackend.modules.article.domain.ArticleManager;
import com.rebwon.realworldbackend.modules.article.domain.Tag;
import com.rebwon.realworldbackend.modules.article.web.response.ArticleListResponse;
import com.rebwon.realworldbackend.modules.article.web.response.ArticleResponse;
import com.rebwon.realworldbackend.modules.member.application.ProfileMember;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArticleFacadeService {

    private final ArticleManager manager;

    public ArticleFacadeService(ArticleManager manager) {
        this.manager = manager;
    }

    public ArticleResponse create(Member member, CreateArticleCommand command) {
        Article article = manager.create(member, command);
        ProfileMember profileMember = new ProfileMember(member);
        return ArticleResponse
            .of(article, toArray(article.getTags()), profileMember);
    }

    private String[] toArray(Set<Tag> tags) {
        return tags.stream().map(Tag::getName).toArray(String[]::new);
    }

    public ArticleResponse findOne(String slug) {
        Article article = manager.findOne(slug);
        ProfileMember profileMember = new ProfileMember(article.getAuthor());
        return ArticleResponse
            .of(article, toArray(article.getTags()), profileMember);
    }

    public ArticleListResponse findAll(String tag, String author, String favorited, int offset,
        int limit) {
        List<ArticleResponse> articleResponses = manager
            .findAll(tag, author, favorited, offset, limit)
            .stream()
            .map(article ->
                ArticleResponse
                    .of(article, toArray(article.getTags()), new ProfileMember(article.getAuthor()))
            ).collect(Collectors.toList());
        return new ArticleListResponse(articleResponses, articleResponses.size());
    }

    public ArticleResponse update(String slug, Member member, UpdateArticleCommand command) {
        Article article = manager.update(slug, member, command);
        ProfileMember profileMember = new ProfileMember(article.getAuthor());
        return ArticleResponse
            .of(article, toArray(article.getTags()), profileMember);
    }

    public void delete(String slug, Member member) {
        manager.delete(slug, member);
    }

    public ArticleResponse favorite(String slug, Member member) {
        Article article = manager.favorite(slug, member);
        ProfileMember profileMember = new ProfileMember(member);
        return ArticleResponse
            .of(article, toArray(article.getTags()), profileMember);
    }

    public ArticleResponse unFavorite(String slug, Member member) {
        Article article = manager.unFavorite(slug, member);
        ProfileMember profileMember = new ProfileMember(member);
        return ArticleResponse
            .of(article, toArray(article.getTags()), profileMember);
    }
}
