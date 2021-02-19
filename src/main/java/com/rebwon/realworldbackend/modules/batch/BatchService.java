package com.rebwon.realworldbackend.modules.batch;

import com.rebwon.realworldbackend.modules.article.domain.Article;
import com.rebwon.realworldbackend.modules.member.domain.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BatchService {
  private final ArticleBatchRepository articleBatchRepository;
  private final MemberBatchRepository memberBatchRepository;

  public void saveAllArticle(List<Article> articles) {
    articleBatchRepository.saveAll(articles);
  }

  public void saveAllMember(List<Member> members) {
    memberBatchRepository.saveAll(members);
  }
}
