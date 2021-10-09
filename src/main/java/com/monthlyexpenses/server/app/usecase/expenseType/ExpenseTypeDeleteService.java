package com.monthlyexpenses.server.app.usecase.expenseType;

import com.monthlyexpenses.server.app.repository.ExpenseTypeRepository;
import com.monthlyexpenses.server.domain.ExpenseType;

public class ExpenseTypeDeleteService {

    private final ExpenseTypeRepository expenseTypeRepository;
    private final ExpenseTypeGetService expenseTypeGetService;

    public ExpenseTypeDeleteService(ExpenseTypeRepository expenseTypeRepository, ExpenseTypeGetService expenseTypeGetService) {
        this.expenseTypeRepository = expenseTypeRepository;
        this.expenseTypeGetService = expenseTypeGetService;
    }

    public void deleteExpenseType(Long expenseTypeId, Long userId) {
        ExpenseType expenseType = expenseTypeGetService.findExpenseTypeByIdAndUserIdOrElseThrow(expenseTypeId, userId);
        expenseTypeRepository.delete(expenseType);
    }
}
