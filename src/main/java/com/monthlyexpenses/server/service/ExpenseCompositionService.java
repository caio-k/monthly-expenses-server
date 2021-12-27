package com.monthlyexpenses.server.service;

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
public class ExpenseCompositionService {

    private final YearService yearService;
    private final ExpenseTypeService expenseTypeService;
    private final InitialMoneyService initialMoneyService;
    private final ExpenseService expenseService;

    public ExpenseResponse getInitializationData(Long customerId) {
        int selectedMonth = -1;
        Integer selectedYearNumber = -1;
        Optional<Year> yearOptional = yearService.findNearestYearFromNow(customerId);

        List<YearResponse> yearResponses = yearService.findAllYearsByCustomerId(customerId);
        List<ExpenseTypeResponse> expenseTypeResponses = expenseTypeService.findAllExpenseTypesByCustomerId(customerId);

        List<ExpenseInfoResponse> expenseInfoResponses = new ArrayList<>();
        InitialMoneyResponse initialMoneyResponse = null;

        if (yearOptional.isPresent()) {
            selectedYearNumber = yearOptional.get().getYearNumber();
            Integer actualYear = GregorianCalendar.getInstance().get(YEAR);

            selectedMonth = selectedYearNumber.equals(actualYear) ?
                    GregorianCalendar.getInstance().get(MONTH) :
                    selectedYearNumber.compareTo(actualYear) < 0 ? DECEMBER : JANUARY;

            initialMoneyResponse = initialMoneyService.findInitialMoneyByCustomerIdAndMonthAndYear(customerId, selectedMonth, yearOptional.get());
            expenseInfoResponses = expenseService.findExpensesByCustomerIdAndMonthAndYear(customerId, selectedMonth, yearOptional.get());
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

    public ExpenseResponseUpdate getAllExpensesAndInitialMoneyByMonthAndYear(Long customerId, int monthNumber, int yearNumber) {
        Year year = yearService.findYearByNumberAndCustomerIdOrElseThrow(yearNumber, customerId);
        InitialMoneyResponse initialMoneyResponse = initialMoneyService.findInitialMoneyByCustomerIdAndMonthAndYear(customerId, monthNumber, year);
        List<ExpenseInfoResponse> expenseInfoResponses = expenseService.findExpensesByCustomerIdAndMonthAndYear(customerId, monthNumber, year);

        return ExpenseResponseUpdate.builder()
                .expenseInfos(expenseInfoResponses)
                .initialMoney(initialMoneyResponse)
                .build();
    }
}
