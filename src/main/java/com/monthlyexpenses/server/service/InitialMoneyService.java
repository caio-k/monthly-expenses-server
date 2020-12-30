package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;
import com.monthlyexpenses.server.error.exception.ResourceNotFoundException;
import com.monthlyexpenses.server.error.exception.UniqueViolationException;
import com.monthlyexpenses.server.message.MessagesComponent;
import com.monthlyexpenses.server.model.*;
import com.monthlyexpenses.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InitialMoneyService {

    private final InitialMoneyRepository initialMoneyRepository;
    private final UserService userService;
    private final YearService yearService;
    private final MonthService monthService;
    private final MonthYearService monthYearService;
    private final MessagesComponent messages;

    @Autowired
    public InitialMoneyService(InitialMoneyRepository initialMoneyRepository, UserService userService,
                               YearService yearService, MonthService monthService, MonthYearService monthYearService,
                               MessagesComponent messages) {
        this.initialMoneyRepository = initialMoneyRepository;
        this.userService = userService;
        this.yearService = yearService;
        this.monthService = monthService;
        this.monthYearService = monthYearService;
        this.messages = messages;
    }

    public InitialMoneyResponse getInitialMoneyByMonthAndYearLogic(Long userId, Month month, Year year) {
        Optional<InitialMoney> initialMoneyOptional =
                initialMoneyRepository.findByMonthYear_MonthAndMonthYear_YearAndUserId(month, year, userId);

        InitialMoneyResponse initialMoneyResponse = null;

        if (initialMoneyOptional.isPresent()) {
            InitialMoney initialMoney = initialMoneyOptional.get();

            initialMoneyResponse = new InitialMoneyResponse(
                    initialMoney.getId(),
                    initialMoney.getMonthYear().getMonth().getMonthNumber(),
                    initialMoney.getMonthYear().getYear().getYearNumber(),
                    initialMoney.getInitialMoney()
            );
        }

        return initialMoneyResponse;
    }

    public ResponseEntity<?> createInitialMoney(Long userId, Integer yearNumber, Integer monthNumber, float initialMoneyValue) {
        Year year = yearService.findByYearNumberAndUserId(yearNumber, userId);
        Month month = monthService.findMonthByMonthNumber(monthNumber);
        MonthYear monthYear = monthYearService.findMonthYearByMonthAndYear(month, year);
        User user = userService.getUserByUserId(userId);

        try {
            InitialMoney initialMoney = new InitialMoney(initialMoneyValue, user, monthYear);
            initialMoneyRepository.save(initialMoney);

            InitialMoneyResponse initialMoneyResponse = new InitialMoneyResponse(
                    initialMoney.getId(),
                    initialMoney.getMonthYear().getMonth().getMonthNumber(),
                    initialMoney.getMonthYear().getYear().getYearNumber(),
                    initialMoney.getInitialMoney()
            );

            return ResponseEntity.ok(initialMoneyResponse);
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("INITIAL_MONEY_ALREADY_EXISTS"));
        }
    }

    public ResponseEntity<?> updateInitialMoney(Long userId, Long initialMoneyId, float initialMoneyValue) {
        InitialMoney initialMoney = findInitialMoneyById(initialMoneyId, userId);
        initialMoney.setInitialMoney(initialMoneyValue);
        initialMoneyRepository.save(initialMoney);

        InitialMoneyResponse initialMoneyResponse = new InitialMoneyResponse(
                initialMoney.getId(),
                initialMoney.getMonthYear().getMonth().getMonthNumber(),
                initialMoney.getMonthYear().getYear().getYearNumber(),
                initialMoney.getInitialMoney()
        );

        return ResponseEntity.ok(initialMoneyResponse);
    }

    private InitialMoney findInitialMoneyById(Long initialMoneyId, Long userId) {
        return initialMoneyRepository.findByIdAndUserId(initialMoneyId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("INITIAL_MONEY_NOT_FOUND")));
    }
}
