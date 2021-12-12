package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YearRepository extends JpaRepository<Year, Long> {

    List<Year> findAllByUserIdOrderByYearNumberDesc(Long userId);

    Optional<Year> findByIdAndUserId(Long id, Long userId);

    Optional<Year> findByYearNumberAndUserId(Integer yearNumber, Long userId);
}
