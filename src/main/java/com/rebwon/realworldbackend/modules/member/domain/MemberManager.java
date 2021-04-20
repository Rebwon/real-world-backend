package com.rebwon.realworldbackend.modules.member.domain;

import com.rebwon.realworldbackend.modules.member.application.command.ProfileUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberManager implements Register, Login {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(MemberNotFoundException::new);
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new PasswordNotMatchedException();
        }
        return member;
    }

    @Override
    public Member register(String email, String username, String password) {
        if (memberRepository.existsByUsername(username)) {
            throw new DuplicateUsernameException("duplicate username");
        }
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException("duplicate email");
        }
        Member member = Member.register(email, username, passwordEncoder.encode(password));
        return memberRepository.save(member);
    }

    public Member find(Member member) {
        return memberRepository.findById(member.getId()).orElseThrow(MemberNotFoundException::new);
    }

    public Member changeProfile(Member member, ProfileUpdateCommand command) {
        if (memberRepository.existsByUsername(command.getUsername())) {
            throw new DuplicateUsernameException("duplicate username");
        }
        if (memberRepository.existsByEmail(command.getEmail())) {
            throw new DuplicateEmailException("duplicate email");
        }
        member.changeProfile(command.getUsername(), command.getEmail(), command.getPassword(),
            command.getBio(), command.getImage());
        return memberRepository.saveAndFlush(member);
    }
}
