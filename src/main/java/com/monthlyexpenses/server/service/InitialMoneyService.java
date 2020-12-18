package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;
import com.monthlyexpenses.server.error.ResourceNotFoundException;
import com.monthlyexpenses.server.error.UniqueViolationException;
import com.monthlyexpenses.server.message.MessagesComponent;
import com.monthlyexpenses.server.model.*;
import com.monthlyexpenses.server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InitialMoneyService {

    private final UserRepository userRepository;

    private final InitialMoneyRepository initialMoneyRepository;

    private final YearRepository yearRepository;

    private final MonthRepository monthRepository;

    private final MonthYearRepository monthYearRepository;

    private final MessagesComponent messages;

    @Autowired
    public InitialMoneyService(UserRepository userRepository, InitialMoneyRepository initialMoneyRepository, YearRepository yearRepository, MonthRepository monthRepository, MonthYearRepository monthYearRepository, MessagesComponent messages) {
        this.userRepository = userRepository;
        this.initialMoneyRepository = initialMoneyRepository;
        this.yearRepository = yearRepository;
        this.monthRepository = monthRepository;
        this.monthYearRepository = monthYearRepository;
        this.messages = messages;
    }

    public ResponseEntity<?> getInitialMoneyByYear(Long userId, Long yearId) {
        Year year = getYearByUserIdAndYearId(userId, yearId);
        List<InitialMoneyResponse> initialMoneyResponses = new ArrayList<>();
        List<InitialMoney> initialMoneyList = initialMoneyRepository.findAllByMonthYear_YearAndUserId(year, userId);

        initialMoneyList.forEach(initialMoney -> initialMoneyResponses.add(
                new InitialMoneyResponse(
                        initialMoney.getId(),
                        initialMoney.getMonthYear().getMonth().getMonthNumber(),
                        initialMoney.getMonthYear().getYear().getYearNumber(),
                        initialMoney.getInitialMoney()
                )
        ));

        return ResponseEntity.ok(initialMoneyResponses);
    }

    public ResponseEntity<?> createInitialMoney(Long userId, Long yearId, Integer monthNumber, float initialMoneyValue) {
        Year year = getYearByUserIdAndYearId(userId, yearId);
        Month month = getMonthByMonthNumber(monthNumber);
        MonthYear monthYear = getMonthYearByMonthAndYear(month, year);
        User user = getUserByUserId(userId);

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
        InitialMoney initialMoney = getInitialMoneyById(initialMoneyId, userId);
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

    private User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("USER_NOT_FOUND")));
    }

    private InitialMoney getInitialMoneyById(Long initialMoneyId, Long userId) {
        return initialMoneyRepository.findByIdAndUserId(initialMoneyId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("INITIAL_MONEY_NOT_FOUND")));
    }

    private Year getYearByUserIdAndYearId(Long userId, Long yearId) {
        return yearRepository.findByIdAndUserId(yearId, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("YEAR_NOT_FOUND")));
    }

    private Month getMonthByMonthNumber(Integer monthNumber) {
        return monthRepository.findByMonthNumber(monthNumber)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("MONTH_NOT_FOUND")));
    }

    private MonthYear getMonthYearByMonthAndYear(Month month, Year year) {
        return monthYearRepository.findByMonthAndYear(month, year)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("MONTH_NOT_FOUND")));
    }
}
