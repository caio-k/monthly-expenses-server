package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.error.exception.ResourceNotFoundException;
import com.monthlyexpenses.server.message.MessagesComponent;
import com.monthlyexpenses.server.model.Month;
import com.monthlyexpenses.server.repository.MonthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonthService {

    private final MonthRepository monthRepository;
    private final MessagesComponent messages;

    @Autowired
    public MonthService(MonthRepository monthRepository, MessagesComponent messages) {
        this.monthRepository = monthRepository;
        this.messages = messages;
    }

    public List<Month> findAll() {
        return monthRepository.findAll();
    }

    public Month findMonthByMonthNumber(Integer monthNumber) {
        return monthRepository.findByMonthNumber(monthNumber)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("MONTH_NOT_FOUND")));
    }
}
