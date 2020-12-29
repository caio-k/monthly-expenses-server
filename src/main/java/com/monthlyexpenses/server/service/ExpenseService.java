package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.expense.ExpenseResponse;
import com.monthlyexpenses.server.dto.response.expense.ExpenseResponseUpdate;
import com.monthlyexpenses.server.dto.response.expenseInfo.ExpenseInfoResponse;
import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import com.monthlyexpenses.server.model.Month;
import com.monthlyexpenses.server.model.Year;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExpenseService {

    private final YearService yearService;
    private final ExpenseTypeService expenseTypeService;
    private final InitialMoneyService initialMoneyService;
    private final ExpenseInfoService expenseInfoService;
    private final MonthService monthService;

    @Autowired
    public ExpenseService(YearService yearService, ExpenseTypeService expenseTypeService,
                          InitialMoneyService initialMoneyService, ExpenseInfoService expenseInfoService,
                          MonthService monthService) {
        this.yearService = yearService;
        this.expenseTypeService = expenseTypeService;
        this.initialMoneyService = initialMoneyService;
        this.expenseInfoService = expenseInfoService;
        this.monthService = monthService;
    }

    public ResponseEntity<?> getInitializationData(Long userId) {
        int selectedMonth = -1;
        Integer selectedYearNumber = -1;
        Optional<Year> yearOptional = yearService.getNearestYearFromNow(userId);

        List<YearResponse> yearResponses = yearService.getAllYearsByUserId(userId);
        List<ExpenseTypeResponse> expenseTypeResponses = expenseTypeService.getAllExpenseTypes(userId);

        List<ExpenseInfoResponse> expenseInfoResponses = new ArrayList<>();
        InitialMoneyResponse initialMoneyResponse = null;

        if (yearOptional.isPresent()) {
            selectedYearNumber = yearOptional.get().getYearNumber();
            Integer actualYear = GregorianCalendar.getInstance().get(Calendar.YEAR);
            int januaryNumber = 0;
            int decemberNumber = 11;

            selectedMonth = selectedYearNumber.equals(actualYear) ?
                    GregorianCalendar.getInstance().get(Calendar.MONTH) :
                    selectedYearNumber.compareTo(actualYear) < 0 ? decemberNumber : januaryNumber;

            Month month = monthService.findMonthByMonthNumber(selectedMonth);
            initialMoneyResponse = initialMoneyService.getInitialMoneyByMonthAndYearLogic(userId, month, yearOptional.get());
            expenseInfoResponses = expenseInfoService.getExpensesByMonthAndYearLogic(userId, month, yearOptional.get());
        }

        return ResponseEntity.ok(new ExpenseResponse(
                selectedYearNumber,
                selectedMonth,
                yearResponses,
                expenseTypeResponses,
                expenseInfoResponses,
                initialMoneyResponse)
        );
    }

    public ResponseEntity<?> getByMonthAndYear(Long userId, int monthNumber, int yearNumber) {
        Year year = yearService.findByYearNumberAndUserId(yearNumber, userId);
        Month month = monthService.findMonthByMonthNumber(monthNumber);
        InitialMoneyResponse initialMoneyResponse = initialMoneyService.getInitialMoneyByMonthAndYearLogic(userId, month, year);
        List<ExpenseInfoResponse> expenseInfoResponses = expenseInfoService.getExpensesByMonthAndYearLogic(userId, month, year);

        return ResponseEntity.ok(new ExpenseResponseUpdate(
                expenseInfoResponses,
                initialMoneyResponse
        ));
    }
}
