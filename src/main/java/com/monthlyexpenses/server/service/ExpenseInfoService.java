package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.expenseInfo.ExpenseInfoResponse;
import com.monthlyexpenses.server.error.exception.ResourceNotFoundException;
import com.monthlyexpenses.server.message.MessagesComponent;
import com.monthlyexpenses.server.model.*;
import com.monthlyexpenses.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseInfoService {

    private final ExpenseInfoRepository expenseInfoRepository;
    private final UserService userService;
    private final MonthYearService monthYearService;
    private final YearService yearService;
    private final ExpenseTypeService expenseTypeService;
    private final MessagesComponent messages;

    @Autowired
    public ExpenseInfoService(ExpenseInfoRepository expenseInfoRepository, UserService userService,
                              MonthYearService monthYearService, YearService yearService,
                              ExpenseTypeService expenseTypeService, MessagesComponent messages) {
        this.expenseInfoRepository = expenseInfoRepository;
        this.userService = userService;
        this.monthYearService = monthYearService;
        this.yearService = yearService;
        this.expenseTypeService = expenseTypeService;
        this.messages = messages;
    }

    public List<ExpenseInfoResponse> getExpensesByMonthAndYearLogic(Long userId, Month month, Year year) {
        MonthYear monthYear = monthYearService.findMonthYearByMonthAndYear(month, year);

        List<ExpenseInfoResponse> expenseInfoResponses = new ArrayList<>();
        List<Expense> expenses = expenseInfoRepository.findAllByMonthYearAndUserId(monthYear, userId);

        expenses.forEach(expense -> expenseInfoResponses.add(
                new ExpenseInfoResponse(
                        expense.getId(),
                        expense.getName(),
                        expense.getPrice(),
                        expense.isPaid(),
                        expense.getMonthYear().getMonth().getMonthNumber(),
                        expense.getMonthYear().getYear().getYearNumber(),
                        expense.getExpenseType().getId()
                )
        ));

        return expenseInfoResponses;
    }

    public ResponseEntity<?> createExpense(Long userId, String name, float price, boolean paid, Long expenseTypeId,
                                           Integer monthNumber, Integer yearNumber) {
        User user = userService.getUserByUserId(userId);
        Year year = yearService.findByYearNumberAndUserId(yearNumber, userId);
        MonthYear monthYear = monthYearService.findMonthYearByMonthNumberAndYearId(monthNumber, year.getId(), userId);
        ExpenseType expenseType = expenseTypeService.findExpenseTypeByIdAndUserId(expenseTypeId, userId);

        Expense expense = new Expense(name, price, paid, expenseType, user, monthYear);
        expenseInfoRepository.save(expense);

        ExpenseInfoResponse expenseInfoResponse = new ExpenseInfoResponse(
                expense.getId(),
                expense.getName(),
                expense.getPrice(),
                expense.isPaid(),
                expense.getMonthYear().getMonth().getMonthNumber(),
                expense.getMonthYear().getYear().getYearNumber(),
                expense.getExpenseType().getId()
        );

        return ResponseEntity.ok(expenseInfoResponse);
    }

    public ResponseEntity<?> updateExpense(Long userId, Long expenseId, String name, float price, boolean paid,
                                           Long expenseTypeId) {
        ExpenseType expenseType = expenseTypeService.findExpenseTypeByIdAndUserId(expenseTypeId, userId);
        Expense expense = getExpenseById(expenseId, userId);

        expense.setName(name);
        expense.setPrice(price);
        expense.setPaid(paid);
        expense.setExpenseType(expenseType);
        expenseInfoRepository.save(expense);

        ExpenseInfoResponse expenseInfoResponse = new ExpenseInfoResponse(
                expense.getId(),
                expense.getName(),
                expense.getPrice(),
                expense.isPaid(),
                expense.getMonthYear().getMonth().getMonthNumber(),
                expense.getMonthYear().getYear().getYearNumber(),
                expense.getExpenseType().getId()
        );

        return ResponseEntity.ok(expenseInfoResponse);
    }

    public ResponseEntity<?> deleteExpense(Long userId, Long expenseId) {
        Expense expense = getExpenseById(expenseId, userId);
        expenseInfoRepository.delete(expense);
        return ResponseEntity.ok(new MessageResponse(messages.get("EXPENSE_DELETED")));
    }

    private Expense getExpenseById(Long id, Long userId) {
        return expenseInfoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("EXPENSE_NOT_FOUND")));
    }
}
