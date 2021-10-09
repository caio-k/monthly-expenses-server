package com.monthlyexpenses.server.app.repository;

import com.monthlyexpenses.server.domain.ExpenseType;

import java.util.List;
import java.util.Optional;

public interface ExpenseTypeRepository {

    ExpenseType save(ExpenseType expenseType);

    Optional<ExpenseType> findExpenseTypeByNameAndUserId(String name, Long userId);

    Optional<ExpenseType> findExpenseTypeByIdAndUserId(Long expenseTypeId, Long userId);

    List<ExpenseType> findAllByUserIdOrderByNameAsc(Long userId);

    void delete(ExpenseType expenseType);
}
