package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.MonthYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MonthYearRepository extends JpaRepository<MonthYear, Long> {

}
