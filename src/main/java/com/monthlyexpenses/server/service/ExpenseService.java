package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.constants.Month;
import com.monthlyexpenses.server.dto.response.expense.ExpenseResponse;
import com.monthlyexpenses.server.dto.response.expense.ExpenseResponseUpdate;
import com.monthlyexpenses.server.dto.response.expenseInfo.ExpenseInfoResponse;
import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import com.monthlyexpenses.server.model.Year;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static java.util.Calendar.*;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final YearService yearService;
    private final ExpenseTypeService expenseTypeService;
    private final InitialMoneyService initialMoneyService;
    private final ExpenseInfoService expenseInfoService;

    public ExpenseResponse getInitializationData(Long customerId) {
        int selectedMonth = -1;
        Integer selectedYearNumber = -1;
        Optional<Year> yearOptional = yearService.getNearestYearFromNow(customerId);

        List<YearResponse> yearResponses = yearService.getAllYearsByUserId(customerId);
        List<ExpenseTypeResponse> expenseTypeResponses = expenseTypeService.getAllExpenseTypes(customerId);

        List<ExpenseInfoResponse> expenseInfoResponses = new ArrayList<>();
        InitialMoneyResponse initialMoneyResponse = null;

        if (yearOptional.isPresent()) {
            selectedYearNumber = yearOptional.get().getYearNumber();
            Integer actualYear = GregorianCalendar.getInstance().get(YEAR);

            selectedMonth = selectedYearNumber.equals(actualYear) ?
                    GregorianCalendar.getInstance().get(MONTH) :
                    selectedYearNumber.compareTo(actualYear) < 0 ? DECEMBER : JANUARY;

            System.out.println("============ " + selectedMonth + " ==============");
            Month month = Month.findByMonthNumber(selectedYearNumber);
            initialMoneyResponse = initialMoneyService.getInitialMoneyByMonthAndYearLogic(customerId, month, yearOptional.get());
            expenseInfoResponses = expenseInfoService.getExpensesByMonthAndYearLogic(customerId, month, yearOptional.get());
        }

        return ExpenseResponse.builder()
                .selectedYearNumber(selectedYearNumber)
                .selectedMonth(selectedMonth)
                .years(yearResponses)
                .expenseTypes(expenseTypeResponses)
                .expenseInfos(expenseInfoResponses)
                .initialMoney(initialMoneyResponse)
                .build();
    }

    public ExpenseResponseUpdate getByMonthAndYear(Long customerId, int monthNumber, int yearNumber) {
        Month month = Month.findByMonthNumber(monthNumber);
        Year year = yearService.findByYearNumberAndUserId(yearNumber, customerId);
        InitialMoneyResponse initialMoneyResponse = initialMoneyService.getInitialMoneyByMonthAndYearLogic(customerId, month, year);
        List<ExpenseInfoResponse> expenseInfoResponses = expenseInfoService.getExpensesByMonthAndYearLogic(customerId, month, year);

        return ExpenseResponseUpdate.builder()
                .expenseInfos(expenseInfoResponses)
                .initialMoney(initialMoneyResponse)
                .build();
    }
}
