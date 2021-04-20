package com.rebwon.realworldbackend.modules.article.domain;

import com.rebwon.realworldbackend.modules.common.domain.ChangeHistory;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member author;

    private ChangeHistory changeHistory;

    private Comment(Long id, String body, Article article, Member author) {
        this.id = id;
        this.body = body;
        this.article = article;
        this.author = author;
        this.changeHistory = new ChangeHistory();
    }

    public static Comment write(String body, Article article, Member author) {
        return new Comment(null, body, article, author);
    }

    public boolean verifyAuthor(Member member) {
        return this.author.equals(member);
    }
}
