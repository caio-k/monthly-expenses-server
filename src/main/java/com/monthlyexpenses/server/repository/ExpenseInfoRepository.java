package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.Expense;
import com.monthlyexpenses.server.model.MonthYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseInfoRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByMonthYearAndUserId(MonthYear monthYear, Long userId);

    Optional<Expense> findByIdAndUserId(Long id, Long userId);
}
