package com.monthlyexpenses.server.app.usecase.expense;

import com.monthlyexpenses.server.app.exception.ResourceNotFoundException;
import com.monthlyexpenses.server.app.repository.ExpenseRepository;
import com.monthlyexpenses.server.domain.Expense;

public class ExpenseGetService {

    private final ExpenseRepository expenseRepository;

    public ExpenseGetService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense findExpenseByIdAndUserIdOrElseThrow(Long expenseId, Long userId) {
        return expenseRepository.findExpenseByIdAndUserId(expenseId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa não encontrada a partir do identificador."));
    }
}
