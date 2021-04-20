package com.rebwon.realworldbackend.modules.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<Member> findByUsername(String username);

    @EntityGraph("member-with-follows")
    Optional<Member> findByEmail(String email);
}
