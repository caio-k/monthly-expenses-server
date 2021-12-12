package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.error.exception.ResourceNotFoundException;
import com.monthlyexpenses.server.message.MessagesComponent;
import com.monthlyexpenses.server.model.Month;
import com.monthlyexpenses.server.model.MonthYear;
import com.monthlyexpenses.server.model.Year;
import com.monthlyexpenses.server.repository.MonthYearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MonthYearService {

    private final MonthYearRepository monthYearRepository;
    private final YearService yearService;
    private final MonthService monthService;
    private final MessagesComponent messages;

    public MonthYear findMonthYearByMonthAndYear(Month month, Year year) {
        return monthYearRepository.findByMonthAndYear(month, year)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("MONTH_NOT_FOUND")));
    }

    public MonthYear findMonthYearByMonthNumberAndYearId(Integer monthNumber, Long yearId, Long userId) {
        Month month = monthService.findMonthByMonthNumber(monthNumber);
        Year year = yearService.findYearByYearIdAndUserId(yearId, userId);
        return findMonthYearByMonthAndYear(month, year);
    }
}
