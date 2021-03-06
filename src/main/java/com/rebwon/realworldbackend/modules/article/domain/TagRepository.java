package com.rebwon.realworldbackend.modules.article.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByName(String name);
}
