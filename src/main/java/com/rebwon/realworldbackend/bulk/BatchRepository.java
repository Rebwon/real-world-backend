package com.rebwon.realworldbackend.bulk;

import java.util.List;

public interface BatchRepository<T> {

  void saveAll(List<T> entities);
}
