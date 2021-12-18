package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.constants.Month;
import com.monthlyexpenses.server.model.Expense;
import com.monthlyexpenses.server.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseInfoRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByMonthAndYearAndCustomerId(Month month, Year year, Long customerId);

    Optional<Expense> findByIdAndCustomerId(Long id, Long customerId);
}
