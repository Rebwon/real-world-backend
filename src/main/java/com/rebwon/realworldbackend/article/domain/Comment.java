package com.rebwon.realworldbackend.article.domain;

import com.rebwon.realworldbackend.common.domain.ChangeHistory;
import com.rebwon.realworldbackend.member.domain.Member;

public class Comment {
  private Long id;
  private String body;
  private Article article;
  private Member author;
  private ChangeHistory changeHistory;
}
