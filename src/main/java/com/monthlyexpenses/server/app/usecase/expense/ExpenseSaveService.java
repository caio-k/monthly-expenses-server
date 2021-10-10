package com.monthlyexpenses.server.app.usecase.expense;

import com.monthlyexpenses.server.app.repository.ExpenseRepository;
import com.monthlyexpenses.server.app.usecase.expenseType.ExpenseTypeGetService;
import com.monthlyexpenses.server.app.usecase.user.UserGetService;
import com.monthlyexpenses.server.app.usecase.year.YearGetService;
import com.monthlyexpenses.server.domain.Expense;
import com.monthlyexpenses.server.domain.ExpenseType;
import com.monthlyexpenses.server.domain.User;
import com.monthlyexpenses.server.domain.Year;
import com.monthlyexpenses.server.domain.enums.Month;

public class ExpenseSaveService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseGetService expenseGetService;
    private final UserGetService userGetService;
    private final YearGetService yearGetService;
    private final ExpenseTypeGetService expenseTypeGetService;

    public ExpenseSaveService(ExpenseRepository expenseRepository, ExpenseGetService expenseGetService,
                              UserGetService userGetService, YearGetService yearGetService,
                              ExpenseTypeGetService expenseTypeGetService) {
        this.expenseRepository = expenseRepository;
        this.expenseGetService = expenseGetService;
        this.userGetService = userGetService;
        this.yearGetService = yearGetService;
        this.expenseTypeGetService = expenseTypeGetService;
    }

    public Expense createExpense(Long userId, String name, float price, boolean paid, Long expenseTypeId,
                                 Month month, Integer yearNumber) {

        User user = userGetService.findUserByIdOrElseThrow(userId);
        Year year = yearGetService.findYearByNumberAndUserIdOrElseThrow(yearNumber, userId);
        ExpenseType expenseType = expenseTypeGetService.findExpenseTypeByIdAndUserIdOrElseThrow(expenseTypeId, userId);
        Expense expense = new Expense(name, price, paid, user, expenseType, month, year);
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long userId, Long expenseId, String name, float price, boolean paid,
                                 Long expenseTypeId) {

        ExpenseType expenseType = expenseTypeGetService.findExpenseTypeByIdAndUserIdOrElseThrow(expenseTypeId, userId);
        Expense expense = expenseGetService.findExpenseByIdAndUserIdOrElseThrow(expenseId, userId);

        expense.setName(name);
        expense.setPrice(price);
        expense.setPaid(paid);
        expense.setExpenseType(expenseType);
        return expenseRepository.save(expense);
    }
}
