package com.rebwon.realworldbackend.article.web;

import com.rebwon.realworldbackend.article.domain.Tag;
import com.rebwon.realworldbackend.article.domain.TagRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagApi {

  private final TagRepository tagRepository;

  public TagApi(TagRepository tagRepository) {
    this.tagRepository = tagRepository;
  }

  @GetMapping("/api/tags")
  public ResponseEntity<List<String>> findAll() {
    return ResponseEntity.ok(
        tagRepository.findAll()
            .stream()
            .map(Tag::getName)
            .collect(Collectors.toList()));
  }
}
