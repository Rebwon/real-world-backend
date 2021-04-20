package com.rebwon.realworldbackend.modules.member.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.rebwon.realworldbackend.modules.PersistenceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

public class MemberPersistenceTest extends PersistenceExtension {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(Member.register("rebwon@gmail.com", "rebwon", "password"));
    }

    @AfterEach
    void tearDown() {
        memberRepository.deleteAll();
    }

    @Test
    @Rollback(value = false)
    void should_member_change_profile_success() {
        Member member = memberRepository.findByUsername("rebwon").get();
        member.changeProfile("rebwon12", "rebwon@naver.com", "password1", "sample bio",
            "sample image");

        assertThat(member.getEmail()).isEqualTo("rebwon@naver.com");
        assertThat(member.getBio()).isEqualTo("sample bio");
        assertThat(member.getImage()).isEqualTo("sample image");
        assertThat(member.getChangeHistory().getCreatedAt())
            .isBefore(member.getChangeHistory().getModifiedAt());
    }
}
