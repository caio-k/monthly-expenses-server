package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.configuration.MessagesComponent;
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

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class InitialMoneyService {

    private final InitialMoneyRepository initialMoneyRepository;
    private final CustomerService customerService;
    private final YearService yearService;
    private final MessagesComponent messages;

    public InitialMoneyResponse findInitialMoneyByCustomerIdAndMonthAndYear(Long customerId, Integer month, Year year) {
        if (isNull(year)) return null;

        return initialMoneyRepository.findByMonthNumberAndYearAndCustomerId(month, year, customerId)
                .map(this::buildInitialMoneyResponse)
                .orElse(null);
    }

    public InitialMoneyResponse createInitialMoney(Long customerId, Integer yearNumber, Integer monthNumber, float initialMoneyValue) {
        Year year = yearService.findYearByNumberAndCustomerIdOrElseThrow(yearNumber, customerId);
        Customer customer = customerService.findCustomerByIdOrElseThrow(customerId);

        InitialMoney initialMoneyToBeSaved = InitialMoney.builder()
                .initialMoney(initialMoneyValue)
                .customer(customer)
                .year(year)
                .monthNumber(monthNumber)
                .build();

        try {
            InitialMoney initialMoneySaved = initialMoneyRepository.saveAndFlush(initialMoneyToBeSaved);
            return buildInitialMoneyResponse(initialMoneySaved);
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("INITIAL_MONEY_ALREADY_EXISTS"));
        }
    }

    public InitialMoneyResponse updateInitialMoney(Long customerId, Long initialMoneyId, float initialMoneyValue) {
        InitialMoney initialMoney = findInitialMoneyByIdAndCustomerIdOrElseThrow(initialMoneyId, customerId);
        initialMoney.setInitialMoney(initialMoneyValue);
        initialMoneyRepository.saveAndFlush(initialMoney);

        return buildInitialMoneyResponse(initialMoney);
    }

    private InitialMoney findInitialMoneyByIdAndCustomerIdOrElseThrow(Long initialMoneyId, Long customerId) {
        return initialMoneyRepository.findByIdAndCustomerId(initialMoneyId, customerId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("INITIAL_MONEY_NOT_FOUND")));
    }

    private InitialMoneyResponse buildInitialMoneyResponse(InitialMoney initialMoney) {
        return InitialMoneyResponse.builder()
                .initialMoneyId(initialMoney.getId())
                .month(initialMoney.getMonthNumber())
                .year(initialMoney.getYear().getYearNumber())
                .initialMoney(initialMoney.getInitialMoney())
                .build();
    }
}
