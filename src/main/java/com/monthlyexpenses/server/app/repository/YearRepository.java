package com.monthlyexpenses.server.app.repository;

import com.monthlyexpenses.server.domain.Year;

import java.util.List;
import java.util.Optional;

public interface YearRepository {

    Year save(Year year);

    Optional<Year> findYearByNumberAndUserId(Integer yearNumber, Long userId);

    Optional<Year> findYearByIdAndUserId(Long id, Long userId);

    List<Year> findAllByUserIdOrderByYearNumberDesc(Long userId);
}
