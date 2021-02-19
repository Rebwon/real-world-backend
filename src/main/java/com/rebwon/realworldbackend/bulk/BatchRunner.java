package com.rebwon.realworldbackend.bulk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BatchRunner implements ApplicationRunner {
  @Autowired
  private BatchService batchService;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    /*long startTime = System.currentTimeMillis();
    List<Article> items = new ArrayList<>();
    //List<Member> items = new ArrayList<>();
    int N = 1500000;
    for (int i = 1; i <= N; i++) {
      // Article.create("setup title"+i, "desc", "body", Member.register("setup"+i+"@gmail.com", "setupUser"+i, "password"))
      items.add(Article.create("setup title"+i, "desc", "body", Member.register("setup"+i+"@gmail.com", "setupUser"+i, "password")));
    }
    batchService.saveAllArticle(items);
    long endTime = System.currentTimeMillis();
    log.info("OOO Elapsed: {} secs", ((endTime - startTime) / 1000.0f));*/
  }
}
