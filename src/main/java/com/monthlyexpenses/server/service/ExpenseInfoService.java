package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.expenseInfo.ExpenseInfoResponse;
import com.monthlyexpenses.server.exceptions.ResourceNotFoundException;
import com.monthlyexpenses.server.configuration.MessagesComponent;
import com.monthlyexpenses.server.model.*;
import com.monthlyexpenses.server.repository.ExpenseInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseInfoService {

    private final ExpenseInfoRepository expenseInfoRepository;
    private final UserService userService;
    private final MonthYearService monthYearService;
    private final YearService yearService;
    private final ExpenseTypeService expenseTypeService;
    private final MessagesComponent messages;

    public List<ExpenseInfoResponse> getExpensesByMonthAndYearLogic(Long userId, Month month, Year year) {
        MonthYear monthYear = monthYearService.findMonthYearByMonthAndYear(month, year);

        return expenseInfoRepository.findAllByMonthYearAndUserId(monthYear, userId)
                .stream().map(this::buildExpenseInfoResponse)
                .collect(Collectors.toList());
    }

    public ExpenseInfoResponse createExpense(Long userId, String name, float price, boolean paid, Long expenseTypeId,
                                             Integer monthNumber, Integer yearNumber) {
        User user = userService.getUserByUserId(userId);
        Year year = yearService.findByYearNumberAndUserId(yearNumber, userId);
        MonthYear monthYear = monthYearService.findMonthYearByMonthNumberAndYearId(monthNumber, year.getId(), userId);
        ExpenseType expenseType = expenseTypeService.findExpenseTypeByIdAndUserId(expenseTypeId, userId);

        Expense expense = new Expense(name, price, paid, expenseType, user, monthYear);
        Expense expenseSaved = expenseInfoRepository.saveAndFlush(expense);

        return buildExpenseInfoResponse(expenseSaved);
    }

    public ExpenseInfoResponse updateExpense(Long userId, Long expenseId, String name, float price, boolean paid,
                                             Long expenseTypeId) {
        ExpenseType expenseType = expenseTypeService.findExpenseTypeByIdAndUserId(expenseTypeId, userId);
        Expense expense = getExpenseById(expenseId, userId);

        expense.setName(name);
        expense.setPrice(price);
        expense.setPaid(paid);
        expense.setExpenseType(expenseType);

        Expense expenseSaved = expenseInfoRepository.saveAndFlush(expense);
        return buildExpenseInfoResponse(expenseSaved);
    }

    public MessageResponse deleteExpense(Long userId, Long expenseId) {
        Expense expense = getExpenseById(expenseId, userId);
        expenseInfoRepository.delete(expense);
        return MessageResponse.builder().message(messages.get("EXPENSE_DELETED")).build();
    }

    private Expense getExpenseById(Long id, Long userId) {
        return expenseInfoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("EXPENSE_NOT_FOUND")));
    }

    private ExpenseInfoResponse buildExpenseInfoResponse(Expense expense) {
        return ExpenseInfoResponse.builder()
                .id(expense.getId())
                .name(expense.getName())
                .price(expense.getPrice())
                .paid(expense.isPaid())
                .month(expense.getMonthYear().getMonth().getMonthNumber())
                .year(expense.getMonthYear().getYear().getYearNumber())
                .expenseTypeId(expense.getExpenseType().getId())
                .build();
    }
}
