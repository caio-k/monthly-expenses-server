package com.monthlyexpenses.server.app.usecase.expenseType;

import com.monthlyexpenses.server.app.exception.ResourceNotFoundException;
import com.monthlyexpenses.server.app.repository.ExpenseTypeRepository;
import com.monthlyexpenses.server.domain.ExpenseType;

import java.util.List;
import java.util.Optional;

public class ExpenseTypeGetService {

    private final ExpenseTypeRepository expenseTypeRepository;

    public ExpenseTypeGetService(ExpenseTypeRepository expenseTypeRepository) {
        this.expenseTypeRepository = expenseTypeRepository;
    }

    public List<ExpenseType> findAllByUserIdOrderByNameAsc(Long userId) {
        return expenseTypeRepository.findAllByUserIdOrderByNameAsc(userId);
    }

    public ExpenseType findExpenseTypeByIdAndUserIdOrElseThrow(Long expenseTypeId, Long userId) {
        return expenseTypeRepository.findExpenseTypeByIdAndUserId(expenseTypeId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível encontrar o tipo de despensa."));
    }

    public Optional<ExpenseType> findExpenseTypeByNameAndUserId(String expenseTypeName, Long userId) {
        return expenseTypeRepository.findExpenseTypeByNameAndUserId(expenseTypeName, userId);
    }
}
