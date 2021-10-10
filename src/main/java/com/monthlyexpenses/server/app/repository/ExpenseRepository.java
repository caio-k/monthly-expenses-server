package com.monthlyexpenses.server.app.repository;

import com.monthlyexpenses.server.domain.Expense;

import java.util.Optional;

public interface ExpenseRepository {

    Expense save(Expense expense);

    Optional<Expense> findExpenseByIdAndUserId(Long expenseId, Long userId);

    void delete(Expense expense);
}
