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

    private static final Integer UNDEFINED_YEAR = -1;
    private static final Integer UNDEFINED_MONTH = -1;

    public ExpenseResponse getInitializationData(Long customerId) {
        List<YearResponse> allYears = yearService.findAllYearsByCustomerId(customerId);
        Optional<Year> nearestYearFromNowOptional = yearService.findNearestYearFromNow(customerId);
        Integer selectedYearNumber = nearestYearFromNowOptional.map(Year::getYearNumber).orElse(UNDEFINED_YEAR);

        int nearestMonthRegisteredFromNow = findNearestMonthRegisteredFromNow(selectedYearNumber);

        Year nearestYearFromNowOrElseNull = nearestYearFromNowOptional.orElse(null);
        InitialMoneyResponse initialMoneyResponse = initialMoneyService.findInitialMoneyByCustomerIdAndMonthAndYear(customerId, nearestMonthRegisteredFromNow, nearestYearFromNowOrElseNull);
        List<ExpenseInfoResponse> expenseInfoResponses = expenseService.findExpensesByCustomerIdAndMonthAndYear(customerId, nearestMonthRegisteredFromNow, nearestYearFromNowOrElseNull);

        List<ExpenseTypeResponse> allExpensesType = expenseTypeService.findAllExpenseTypesByCustomerId(customerId);

        return ExpenseResponse.builder()
                .selectedYearNumber(selectedYearNumber)
                .selectedMonth(nearestMonthRegisteredFromNow)
                .years(allYears)
                .expenseTypes(allExpensesType)
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

    private Integer findNearestMonthRegisteredFromNow(Integer selectedYearNumber) {
        if (UNDEFINED_YEAR.equals(selectedYearNumber)) {
            return UNDEFINED_MONTH;
        }

        Integer actualYear = GregorianCalendar.getInstance().get(YEAR);

        return selectedYearNumber.equals(actualYear) ?
                GregorianCalendar.getInstance().get(MONTH) :
                selectedYearNumber.compareTo(actualYear) < 0 ? DECEMBER : JANUARY;
    }
}
