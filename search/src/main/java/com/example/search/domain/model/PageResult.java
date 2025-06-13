package com.example.search.domain.model;

import java.util.List;

public record PageResult(
      List<PersonProfile> items,
      long total,
      int page,
      int size
) {}
