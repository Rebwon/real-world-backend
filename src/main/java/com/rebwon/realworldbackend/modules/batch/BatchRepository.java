package com.rebwon.realworldbackend.modules.batch;

import java.util.List;

public interface BatchRepository<T> {

  void saveAll(List<T> entities);
}
