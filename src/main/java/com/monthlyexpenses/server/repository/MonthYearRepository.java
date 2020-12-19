package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.Month;
import com.monthlyexpenses.server.model.MonthYear;
import com.monthlyexpenses.server.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonthYearRepository extends JpaRepository<MonthYear, Long> {

    Optional<MonthYear> findByMonthAndYear(Month month, Year year);
}
