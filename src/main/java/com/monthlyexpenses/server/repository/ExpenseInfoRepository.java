package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.Expense;
import com.monthlyexpenses.server.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseInfoRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByMonthNumberAndYearAndCustomerId(Integer monthNumber, Year year, Long customer_id);

    Optional<Expense> findByIdAndCustomerId(Long id, Long customerId);
}
