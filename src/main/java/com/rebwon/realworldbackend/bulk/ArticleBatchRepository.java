package com.rebwon.realworldbackend.bulk;

import com.rebwon.realworldbackend.article.domain.Article;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArticleBatchRepository implements BatchRepository<Article> {
  private final JdbcTemplate jdbcTemplate;

  @Value("${batchSize}")
  private int batchSize;

  @Override
  public void saveAll(List<Article> entities) {
    int batchCount = 0;
    List<Article> subItems = new ArrayList<>();
    for (int i = 0; i < entities.size(); i++) {
      subItems.add(entities.get(i));
      if ((i + 1) % batchSize == 0) {
        batchCount = batchInsert(batchSize, batchCount, subItems);
      }
    }
    if (!subItems.isEmpty()) {
      batchCount = batchInsert(batchSize, batchCount, subItems);
    }
    System.out.println("batchCount: " + batchCount);
  }

  private int batchInsert(int batchSize, int batchCount, List<Article> subItems) {
    jdbcTemplate.batchUpdate("INSERT INTO article (body, created_at, description, modified_at, slug, title, author_id) VALUES (?, ?, ?, ?, ?, ?, ?)",
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setString(1, subItems.get(i).getBody());
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(3, subItems.get(i).getDescription());
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(5, subItems.get(i).getSlug().value());
            ps.setString(6, subItems.get(i).getTitle());
            ps.setInt(7, i+1);
          }
          @Override
          public int getBatchSize() {
            return subItems.size();
          }
        });
    subItems.clear();
    batchCount++;
    return batchCount;
  }
}
