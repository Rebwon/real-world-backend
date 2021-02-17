package com.rebwon.realworldbackend.common.domain;

import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import lombok.ToString;

@Embeddable
@ToString
@Getter
public class ChangeHistory {

  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  @PrePersist
  void prePersist() {
    this.createdAt = LocalDateTime.now();
    this.modifiedAt = LocalDateTime.now();
  }

  @PreUpdate
  void preUpdate() {
    this.modifiedAt = LocalDateTime.now();
  }

}
