package com.monthlyexpenses.server.app.usecase.expense;

import com.monthlyexpenses.server.app.repository.ExpenseRepository;
import com.monthlyexpenses.server.domain.Expense;

public class ExpenseDeleteService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseGetService expenseGetService;

    public ExpenseDeleteService(ExpenseRepository expenseRepository, ExpenseGetService expenseGetService) {
        this.expenseRepository = expenseRepository;
        this.expenseGetService = expenseGetService;
    }

    public void delete(Long userId, Long expenseId) {
        Expense expense = expenseGetService.findExpenseByIdAndUserIdOrElseThrow(expenseId, userId);
        expenseRepository.delete(expense);
    }
}
