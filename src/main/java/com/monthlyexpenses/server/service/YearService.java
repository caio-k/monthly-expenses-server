package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import com.monthlyexpenses.server.error.exception.ResourceNotFoundException;
import com.monthlyexpenses.server.error.exception.UniqueViolationException;
import com.monthlyexpenses.server.message.MessagesComponent;
import com.monthlyexpenses.server.model.Month;
import com.monthlyexpenses.server.model.MonthYear;
import com.monthlyexpenses.server.model.User;
import com.monthlyexpenses.server.model.Year;
import com.monthlyexpenses.server.repository.MonthYearRepository;
import com.monthlyexpenses.server.repository.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class YearService {

    private final YearRepository yearRepository;
    private final MonthYearRepository monthYearRepository;
    private final UserService userService;
    private final MonthService monthService;
    private final MessagesComponent messages;

    @Autowired
    public YearService(YearRepository yearRepository, MonthYearRepository monthYearRepository,
                       UserService userService, MonthService monthService,
                       MessagesComponent messages) {
        this.yearRepository = yearRepository;
        this.monthYearRepository = monthYearRepository;
        this.userService = userService;
        this.monthService = monthService;
        this.messages = messages;
    }

    public List<YearResponse> getAllYearsByUserId(Long userId) {
        List<YearResponse> yearResponses = new ArrayList<>();
        List<Year> years = yearRepository.findAllByUserIdOrderByYearNumberDesc(userId);

        for (Year year : years) {
            yearResponses.add(new YearResponse(year.getId(), year.getYearNumber()));
        }

        return yearResponses;
    }

    public ResponseEntity<?> createYear(Long userId, Integer yearNumber) {
        User user = userService.getUserByUserId(userId);
        Year year = new Year(yearNumber, user);

        try {
            yearRepository.save(year);
            List<Month> months = monthService.findAll();
            months.forEach(month -> monthYearRepository.save(new MonthYear(month, year)));
            return ResponseEntity.ok(new YearResponse(year.getId(), year.getYearNumber()));
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("YEAR_ALREADY_EXISTS"));
        }
    }

    public ResponseEntity<?> updateYear(Long userId, Long yearId, Integer yearNumber) {
        Year year = findYearByYearIdAndUserId(yearId, userId);

        try {
            year.setYearNumber(yearNumber);
            yearRepository.save(year);
            return ResponseEntity.ok(new YearResponse(year.getId(), year.getYearNumber()));
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("YEAR_ALREADY_EXISTS"));
        }
    }

    public ResponseEntity<?> deleteYear(Long userId, Long yearId) {
        Year year = findYearByYearIdAndUserId(yearId, userId);
        yearRepository.delete(year);
        return ResponseEntity.ok(new MessageResponse(messages.get("YEAR_DELETED")));
    }

    public Optional<Year> getNearestYearFromNow(Long userId) {
        List<Year> years = yearRepository.findAllByUserIdOrderByYearNumberDesc(userId);
        Integer actualYear = GregorianCalendar.getInstance().get(Calendar.YEAR);
        int index = 0;

        while (index < years.size() && years.get(index).getYearNumber().compareTo(actualYear) > 0) {
            index++;
        }

        if (years.isEmpty()) return Optional.empty();

        if (index < years.size()) {
            if (index > 0 && years.get(index).getYearNumber().compareTo(actualYear) < 0) {
                index--;
            }
        } else {
            index--;
        }

        return Optional.of(years.get(index));
    }

    public Year findYearByYearIdAndUserId(Long id, Long userId) {
        return yearRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("YEAR_NOT_FOUND")));
    }

    public Year findByYearNumberAndUserId(Integer yearNumber, Long userId) {
        return yearRepository.findByYearNumberAndUserId(yearNumber, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("YEAR_NOT_FOUND")));
    }
}
