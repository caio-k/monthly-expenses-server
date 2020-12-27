package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.expense.ExpenseResponse;
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

    private final MonthYearRepository monthYearRepository;

    private final MonthRepository monthRepository;

    private final YearRepository yearRepository;

    private final UserRepository userRepository;

    private final ExpenseTypeRepository expenseTypeRepository;

    private final MessagesComponent messages;

    @Autowired
    public ExpenseInfoService(ExpenseInfoRepository expenseInfoRepository, MonthYearRepository monthYearRepository,
                              MonthRepository monthRepository, YearRepository yearRepository, UserRepository userRepository,
                              ExpenseTypeRepository expenseTypeRepository, MessagesComponent messages) {
        this.expenseInfoRepository = expenseInfoRepository;
        this.monthYearRepository = monthYearRepository;
        this.monthRepository = monthRepository;
        this.yearRepository = yearRepository;
        this.userRepository = userRepository;
        this.expenseTypeRepository = expenseTypeRepository;
        this.messages = messages;
    }

    public ResponseEntity<?> getExpensesByMonthAndYear(Long userId, Integer monthNumber, Long yearId) {
        MonthYear monthYear = getMonthYearByMonthNumberAndYearId(monthNumber, yearId, userId);

        List<ExpenseResponse> expenseResponses = new ArrayList<>();
        List<Expense> expenses = expenseInfoRepository.findAllByMonthYearAndUserId(monthYear, userId);

        expenses.forEach(expense -> expenseResponses.add(
                new ExpenseResponse(
                        expense.getId(),
                        expense.getName(),
                        expense.getPrice(),
                        expense.isPaid(),
                        expense.getMonthYear().getMonth().getMonthNumber(),
                        expense.getMonthYear().getYear().getYearNumber(),
                        expense.getExpenseType().getId()
                )
        ));

        return ResponseEntity.ok(expenseResponses);
    }

    public ResponseEntity<?> createExpense(Long userId, String name, float price, boolean paid, Long expenseTypeId,
                                           Integer monthNumber, Long yearId) {
        User user = getUserByUserId(userId);
        MonthYear monthYear = getMonthYearByMonthNumberAndYearId(monthNumber, yearId, userId);
        ExpenseType expenseType = getExpenseTypeById(expenseTypeId, userId);

        Expense expense = new Expense(name, price, paid, expenseType, user, monthYear);
        expenseInfoRepository.save(expense);

        ExpenseResponse expenseResponse = new ExpenseResponse(
                expense.getId(),
                expense.getName(),
                expense.getPrice(),
                expense.isPaid(),
                expense.getMonthYear().getMonth().getMonthNumber(),
                expense.getMonthYear().getYear().getYearNumber(),
                expense.getExpenseType().getId()
        );

        return ResponseEntity.ok(expenseResponse);
    }

    public ResponseEntity<?> updateExpense(Long userId, Long expenseId, String name, float price, boolean paid,
                                           Long expenseTypeId, Integer monthNumber, Long yearId) {
        MonthYear monthYear = getMonthYearByMonthNumberAndYearId(monthNumber, yearId, userId);
        ExpenseType expenseType = getExpenseTypeById(expenseTypeId, userId);
        Expense expense = getExpenseById(expenseId, userId);

        expense.setName(name);
        expense.setPrice(price);
        expense.setPaid(paid);
        expense.setExpenseType(expenseType);
        expense.setMonthYear(monthYear);
        expenseInfoRepository.save(expense);

        ExpenseResponse expenseResponse = new ExpenseResponse(
                expense.getId(),
                expense.getName(),
                expense.getPrice(),
                expense.isPaid(),
                expense.getMonthYear().getMonth().getMonthNumber(),
                expense.getMonthYear().getYear().getYearNumber(),
                expense.getExpenseType().getId()
        );

        return ResponseEntity.ok(expenseResponse);
    }

    public ResponseEntity<?> deleteExpense(Long userId, Long expenseId) {
        Expense expense = getExpenseById(expenseId, userId);
        expenseInfoRepository.delete(expense);
        return ResponseEntity.ok(new MessageResponse(messages.get("EXPENSE_DELETED")));
    }

    private User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("USER_NOT_FOUND")));
    }

    private Month getMonthByMonthNumber(Integer monthNumber) {
        return monthRepository.findByMonthNumber(monthNumber)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("MONTH_NOT_FOUND")));
    }

    private Year getYearByUserIdAndYearId(Long userId, Long yearId) {
        return yearRepository.findByIdAndUserId(yearId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("YEAR_NOT_FOUND")));
    }

    private MonthYear getMonthYearByMonthAndYear(Month month, Year year) {
        return monthYearRepository.findByMonthAndYear(month, year)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("MONTH_NOT_FOUND")));
    }

    private ExpenseType getExpenseTypeById(Long id, Long userId) {
        return expenseTypeRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("EXPENSE_TYPE_NOT_FOUND")));
    }

    private Expense getExpenseById(Long id, Long userId) {
        return expenseInfoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("EXPENSE_NOT_FOUND")));
    }

    private MonthYear getMonthYearByMonthNumberAndYearId(Integer monthNumber, Long yearId, Long userId) {
        Month month = getMonthByMonthNumber(monthNumber);
        Year year = getYearByUserIdAndYearId(userId, yearId);
        return getMonthYearByMonthAndYear(month, year);
    }
}
