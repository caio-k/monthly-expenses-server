package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YearRepository extends JpaRepository<Year, Long> {

    List<Year> findAllByCustomerIdOrderByYearNumberDesc(Long customerId);

    Optional<Year> findByIdAndCustomerId(Long id, Long customerId);

    Optional<Year> findByYearNumberAndCustomerId(Integer yearNumber, Long customerId);
}
