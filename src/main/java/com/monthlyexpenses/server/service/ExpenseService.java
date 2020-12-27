package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.expense.ExpenseResponse;
import com.monthlyexpenses.server.dto.response.expenseInfo.ExpenseInfoResponse;
import com.monthlyexpenses.server.dto.response.expenseType.ExpenseTypeResponse;
import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
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

    @Autowired
    public ExpenseService(YearService yearService, ExpenseTypeService expenseTypeService,
                          InitialMoneyService initialMoneyService, ExpenseInfoService expenseInfoService) {
        this.yearService = yearService;
        this.expenseTypeService = expenseTypeService;
        this.initialMoneyService = initialMoneyService;
        this.expenseInfoService = expenseInfoService;
    }

    public ResponseEntity<?> getInitializationData(Long userId) {
        Optional<Year> yearOptional = yearService.getNearestYearFromNow(userId);

        List<YearResponse> yearResponses = yearService.getAllYearsByUserId(userId);
        List<ExpenseTypeResponse> expenseTypeResponses = expenseTypeService.getAllExpenseTypes(userId);

        List<ExpenseInfoResponse> expenseInfoResponses = new ArrayList<>();
        List<InitialMoneyResponse> initialMoneyResponses = new ArrayList<>();

        if (yearOptional.isPresent()) {
            initialMoneyResponses = initialMoneyService.getInitialMoneyByYearLogic(userId, yearOptional.get().getId());
            Integer nearestYear = yearOptional.get().getYearNumber();
            Integer actualYear = GregorianCalendar.getInstance().get(Calendar.YEAR);
            int month = nearestYear.equals(actualYear) ?
                    GregorianCalendar.getInstance().get(Calendar.MONTH) :
                    nearestYear.compareTo(actualYear) < 0 ? 11 : 0;

            expenseInfoResponses = expenseInfoService.getExpensesByMonthAndYearLogic(userId, month, yearOptional.get().getId());
        }

        return ResponseEntity.ok(new ExpenseResponse(yearResponses, expenseTypeResponses, expenseInfoResponses, initialMoneyResponses));
    }
}
