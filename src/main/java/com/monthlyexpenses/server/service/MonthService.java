package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.exceptions.ResourceNotFoundException;
import com.monthlyexpenses.server.configuration.MessagesComponent;
import com.monthlyexpenses.server.model.Month;
import com.monthlyexpenses.server.repository.MonthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonthService {

    private final MonthRepository monthRepository;
    private final MessagesComponent messages;

    public List<Month> findAll() {
        return monthRepository.findAll();
    }

    public Month findMonthByMonthNumber(Integer monthNumber) {
        return monthRepository.findByMonthNumber(monthNumber)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("MONTH_NOT_FOUND")));
    }
}
