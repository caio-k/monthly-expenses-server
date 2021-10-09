package com.monthlyexpenses.server.app.usecase.expenseType;

import com.monthlyexpenses.server.app.exception.ResourceAlreadyExistsException;
import com.monthlyexpenses.server.app.repository.ExpenseTypeRepository;
import com.monthlyexpenses.server.app.usecase.user.UserGetService;
import com.monthlyexpenses.server.domain.ExpenseType;
import com.monthlyexpenses.server.domain.User;

import java.util.Optional;

import static java.lang.String.format;

public class ExpenseTypeSaveService {

    private final ExpenseTypeRepository expenseTypeRepository;
    private final ExpenseTypeGetService expenseTypeGetService;
    private final UserGetService userGetService;

    public ExpenseTypeSaveService(ExpenseTypeRepository expenseTypeRepository, ExpenseTypeGetService expenseTypeGetService,
                                  UserGetService userGetService) {
        this.expenseTypeRepository = expenseTypeRepository;
        this.expenseTypeGetService = expenseTypeGetService;
        this.userGetService = userGetService;
    }

    public ExpenseType createExpenseType(Long userId, String expenseTypeName) {
        User user = userGetService.findUserByIdOrElseThrow(userId);
        ExpenseType expenseType = new ExpenseType(expenseTypeName, user);

        validateExpenseTypeNameExistence(userId, expenseTypeName);

        return expenseTypeRepository.save(expenseType);
    }

    public ExpenseType updateExpenseType(Long userId, Long expenseTypeId, String newExpenseTypeName) {
        ExpenseType expenseType = expenseTypeGetService.findExpenseTypeByIdAndUserIdOrElseThrow(expenseTypeId, userId);
        expenseType.setName(newExpenseTypeName);

        validateExpenseTypeNameExistence(userId, newExpenseTypeName);

        return expenseTypeRepository.save(expenseType);
    }

    private void validateExpenseTypeNameExistence(Long userId, String expenseTypeName) {
        Optional<ExpenseType> expenseTypeOptional = expenseTypeGetService.findExpenseTypeByNameAndUserId(expenseTypeName, userId);

        if (expenseTypeOptional.isPresent()) {
            throw new ResourceAlreadyExistsException(format("O tipo de despesa %s já está em uso.", expenseTypeName));
        }
    }
}
