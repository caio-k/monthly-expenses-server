package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.configuration.MessagesComponent;
import com.monthlyexpenses.server.constants.Month;
import com.monthlyexpenses.server.dto.response.initialMoney.InitialMoneyResponse;
import com.monthlyexpenses.server.exceptions.ResourceNotFoundException;
import com.monthlyexpenses.server.exceptions.UniqueViolationException;
import com.monthlyexpenses.server.model.Customer;
import com.monthlyexpenses.server.model.InitialMoney;
import com.monthlyexpenses.server.model.Year;
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
    private final MessagesComponent messages;

    public InitialMoneyResponse getInitialMoneyByMonthAndYearLogic(Long customerId, Month month, Year year) {
        return initialMoneyRepository.findByMonthAndYearAndCustomerId(month, year, customerId)
                .map(this::buildInitialMoneyResponse)
                .orElse(null);
    }

    public InitialMoneyResponse createInitialMoney(Long customerId, Integer yearNumber, Integer monthNumber, float initialMoneyValue) {
        Year year = yearService.findByYearNumberAndUserId(yearNumber, customerId);
        Customer customer = userService.getUserByUserId(customerId);

        InitialMoney initialMoneyToBeSaved = InitialMoney.builder()
                .initialMoney(initialMoneyValue)
                .customer(customer)
                .year(year)
                .month(Month.findByMonthNumber(monthNumber))
                .build();

        try {
            InitialMoney initialMoneySaved = initialMoneyRepository.saveAndFlush(initialMoneyToBeSaved);
            return buildInitialMoneyResponse(initialMoneySaved);
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("INITIAL_MONEY_ALREADY_EXISTS"));
        }
    }

    public InitialMoneyResponse updateInitialMoney(Long customerId, Long initialMoneyId, float initialMoneyValue) {
        InitialMoney initialMoney = findInitialMoneyById(initialMoneyId, customerId);
        initialMoney.setInitialMoney(initialMoneyValue);
        initialMoneyRepository.saveAndFlush(initialMoney);

        return buildInitialMoneyResponse(initialMoney);
    }

    private InitialMoney findInitialMoneyById(Long initialMoneyId, Long customerId) {
        return initialMoneyRepository.findByIdAndCustomerId(initialMoneyId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("INITIAL_MONEY_NOT_FOUND")));
    }

    private InitialMoneyResponse buildInitialMoneyResponse(InitialMoney initialMoney) {
        return InitialMoneyResponse.builder()
                .initialMoneyId(initialMoney.getId())
                .month(initialMoney.getMonth().getNumber())
                .year(initialMoney.getYear().getYearNumber())
                .initialMoney(initialMoney.getInitialMoney())
                .build();
    }
}
