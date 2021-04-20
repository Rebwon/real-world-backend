package com.rebwon.realworldbackend.modules.member.domain;

import com.rebwon.realworldbackend.modules.member.application.ProfileMember;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FollowManager {

    private final MemberRepository memberRepository;

    public ProfileMember follow(Member member, String username) {
        Member target = memberRepository.findByUsername(username)
            .orElseThrow(MemberNotFoundException::new);
        member.follow(target);
        return new ProfileMember(target, member.followed(target));
    }

    public ProfileMember unfollow(Member member, String username) {
        Member target = memberRepository.findByUsername(username)
            .orElseThrow(MemberNotFoundException::new);
        member.unfollow(target);
        return new ProfileMember(target, member.followed(target));
    }

    public ProfileMember find(Member member, String username) {
        Member target = memberRepository.findByUsername(username)
            .orElseThrow(MemberNotFoundException::new);
        if (Optional.ofNullable(member).isEmpty()) {
            return new ProfileMember(target);
        }
        return new ProfileMember(target, member.followed(target));
    }
}
