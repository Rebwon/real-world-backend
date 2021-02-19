package com.rebwon.realworldbackend.modules.batch;

import com.rebwon.realworldbackend.modules.member.domain.Member;
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
public class MemberBatchRepository implements BatchRepository<Member>{
  private final JdbcTemplate jdbcTemplate;

  @Value("${batchSize}")
  private int batchSize;

  @Override
  public void saveAll(List<Member> entities) {
    int batchCount = 0;
    List<Member> subItems = new ArrayList<>();
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

  private int batchInsert(int batchSize, int batchCount, List<Member> subItems) {
    jdbcTemplate.batchUpdate("INSERT INTO member (bio, created_at, email, image, modified_at, password, username) VALUES (?, ?, ?, ?, ?, ?, ?)",
        new BatchPreparedStatementSetter() {
          @Override
          public void setValues(PreparedStatement ps, int i) throws SQLException {
            ps.setString(1, subItems.get(i).getBio());
            ps.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(3, subItems.get(i).getEmail());
            ps.setString(4, subItems.get(i).getImage());
            ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(6, subItems.get(i).getPassword());
            ps.setString(7, subItems.get(i).getUsername());
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
