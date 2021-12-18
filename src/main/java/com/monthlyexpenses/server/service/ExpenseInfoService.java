package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.configuration.MessagesComponent;
import com.monthlyexpenses.server.constants.Month;
import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.expenseInfo.ExpenseInfoResponse;
import com.monthlyexpenses.server.exceptions.ResourceNotFoundException;
import com.monthlyexpenses.server.model.Expense;
import com.monthlyexpenses.server.model.ExpenseType;
import com.monthlyexpenses.server.model.Customer;
import com.monthlyexpenses.server.model.Year;
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
    private final YearService yearService;
    private final ExpenseTypeService expenseTypeService;
    private final MessagesComponent messages;

    public List<ExpenseInfoResponse> getExpensesByMonthAndYearLogic(Long customerId, Month month, Year year) {
        return expenseInfoRepository.findAllByMonthAndYearAndCustomerId(month, year, customerId)
                .stream().map(this::buildExpenseInfoResponse)
                .collect(Collectors.toList());
    }

    public ExpenseInfoResponse createExpense(Long customerId, String name, float price, boolean paid, Long expenseTypeId,
                                             Integer monthNumber, Integer yearNumber) {
        Customer customer = userService.getUserByUserId(customerId);
        Year year = yearService.findByYearNumberAndUserId(yearNumber, customerId);
        ExpenseType expenseType = expenseTypeService.findExpenseTypeByIdAndUserId(expenseTypeId, customerId);

        Expense expense = new Expense(name, price, paid, expenseType, customer, year, Month.findByMonthNumber(monthNumber));
        Expense expenseSaved = expenseInfoRepository.saveAndFlush(expense);

        return buildExpenseInfoResponse(expenseSaved);
    }

    public ExpenseInfoResponse updateExpense(Long customerId, Long expenseId, String name, float price, boolean paid,
                                             Long expenseTypeId) {
        ExpenseType expenseType = expenseTypeService.findExpenseTypeByIdAndUserId(expenseTypeId, customerId);
        Expense expense = getExpenseById(expenseId, customerId);

        expense.setName(name);
        expense.setPrice(price);
        expense.setPaid(paid);
        expense.setExpenseType(expenseType);

        Expense expenseSaved = expenseInfoRepository.saveAndFlush(expense);
        return buildExpenseInfoResponse(expenseSaved);
    }

    public MessageResponse deleteExpense(Long customerId, Long expenseId) {
        Expense expense = getExpenseById(expenseId, customerId);
        expenseInfoRepository.delete(expense);
        return MessageResponse.builder().message(messages.get("EXPENSE_DELETED")).build();
    }

    private Expense getExpenseById(Long id, Long customerId) {
        return expenseInfoRepository.findByIdAndCustomerId(id, customerId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("EXPENSE_NOT_FOUND")));
    }

    private ExpenseInfoResponse buildExpenseInfoResponse(Expense expense) {
        return ExpenseInfoResponse.builder()
                .id(expense.getId())
                .name(expense.getName())
                .price(expense.getPrice())
                .paid(expense.isPaid())
                .month(expense.getMonth().getNumber())
                .year(expense.getYear().getYearNumber())
                .expenseTypeId(expense.getExpenseType().getId())
                .build();
    }
}
