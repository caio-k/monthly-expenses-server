package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;
import com.monthlyexpenses.server.exceptions.ResourceNotFoundException;
import com.monthlyexpenses.server.exceptions.UniqueViolationException;
import com.monthlyexpenses.server.configuration.MessagesComponent;
import com.monthlyexpenses.server.model.*;
import com.monthlyexpenses.server.repository.InitialMoneyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitialMoneyService {

    private final InitialMoneyRepository initialMoneyRepository;
    private final UserService userService;
    private final YearService yearService;
    private final MonthService monthService;
    private final MonthYearService monthYearService;
    private final MessagesComponent messages;

    public InitialMoneyResponse getInitialMoneyByMonthAndYearLogic(Long userId, Month month, Year year) {
        return initialMoneyRepository.findByMonthYear_MonthAndMonthYear_YearAndUserId(month, year, userId)
                .map(this::buildInitialMoneyResponse)
                .orElse(null);
    }

    public InitialMoneyResponse createInitialMoney(Long userId, Integer yearNumber, Integer monthNumber, float initialMoneyValue) {
        Year year = yearService.findByYearNumberAndUserId(yearNumber, userId);
        Month month = monthService.findMonthByMonthNumber(monthNumber);
        MonthYear monthYear = monthYearService.findMonthYearByMonthAndYear(month, year);
        User user = userService.getUserByUserId(userId);

        try {
            InitialMoney initialMoneyToBeSaved = new InitialMoney(initialMoneyValue, user, monthYear);
            InitialMoney initialMoneySaved = initialMoneyRepository.saveAndFlush(initialMoneyToBeSaved);
            return buildInitialMoneyResponse(initialMoneySaved);
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("INITIAL_MONEY_ALREADY_EXISTS"));
        }
    }

    public InitialMoneyResponse updateInitialMoney(Long userId, Long initialMoneyId, float initialMoneyValue) {
        InitialMoney initialMoney = findInitialMoneyById(initialMoneyId, userId);
        initialMoney.setInitialMoney(initialMoneyValue);
        initialMoneyRepository.saveAndFlush(initialMoney);

        return buildInitialMoneyResponse(initialMoney);
    }

    private InitialMoney findInitialMoneyById(Long initialMoneyId, Long userId) {
        return initialMoneyRepository.findByIdAndUserId(initialMoneyId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("INITIAL_MONEY_NOT_FOUND")));
    }

    private InitialMoneyResponse buildInitialMoneyResponse(InitialMoney initialMoney) {
        return InitialMoneyResponse.builder()
                .initialMoneyId(initialMoney.getId())
                .month(initialMoney.getMonthYear().getMonth().getMonthNumber())
                .year(initialMoney.getMonthYear().getYear().getYearNumber())
                .initialMoney(initialMoney.getInitialMoney())
                .build();
    }
}
