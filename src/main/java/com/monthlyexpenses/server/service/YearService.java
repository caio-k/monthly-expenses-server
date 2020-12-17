package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import com.monthlyexpenses.server.error.ResourceNotFoundException;
import com.monthlyexpenses.server.error.UniqueViolationException;
import com.monthlyexpenses.server.message.MessagesComponent;
import com.monthlyexpenses.server.model.Month;
import com.monthlyexpenses.server.model.MonthYear;
import com.monthlyexpenses.server.model.User;
import com.monthlyexpenses.server.model.Year;
import com.monthlyexpenses.server.repository.MonthRepository;
import com.monthlyexpenses.server.repository.MonthYearRepository;
import com.monthlyexpenses.server.repository.UserRepository;
import com.monthlyexpenses.server.repository.YearRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class YearService {

    private final YearRepository yearRepository;

    private final UserRepository userRepository;

    private final MonthRepository monthRepository;

    private final MonthYearRepository monthYearRepository;

    private final MessagesComponent messages;

    @Autowired
    public YearService(YearRepository yearRepository, UserRepository userRepository, MonthRepository monthRepository,
                       MonthYearRepository monthYearRepository, MessagesComponent messages) {
        this.yearRepository = yearRepository;
        this.userRepository = userRepository;
        this.monthRepository = monthRepository;
        this.monthYearRepository = monthYearRepository;
        this.messages = messages;
    }

    public ResponseEntity<?> getAllYearsByUserId(Long userId) {
        List<YearResponse> yearResponses = new ArrayList<>();
        List<Year> years = yearRepository.findAllByUserIdOrderByYearNumberDesc(userId);

        for (Year year : years) {
            yearResponses.add(new YearResponse(year.getId(), year.getYearNumber()));
        }

        return ResponseEntity.ok(yearResponses);
    }

    public ResponseEntity<?> createYear(Long userId, Integer yearNumber) {
        Year year = new Year(yearNumber, getUserByUserId(userId));

        try {
            yearRepository.save(year);
            List<Month> months = monthRepository.findAll();
            months.forEach(month -> monthYearRepository.save(new MonthYear(month, year)));
            return ResponseEntity.ok(new YearResponse(year.getId(), year.getYearNumber()));
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("YEAR_ALREADY_EXISTS"));
        }
    }

    public ResponseEntity<?> updateYear(Long userId, Long yearId, Integer yearNumber) {
        Year year = getYearByYearIdAndUserId(yearId, userId);

        try {
            year.setYearNumber(yearNumber);
            yearRepository.save(year);
            return ResponseEntity.ok(new YearResponse(year.getId(), year.getYearNumber()));
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("YEAR_ALREADY_EXISTS"));
        }
    }

    public ResponseEntity<?> deleteYear(Long userId, Long yearId) {
        Year year = getYearByYearIdAndUserId(yearId, userId);
        yearRepository.delete(year);
        return ResponseEntity.ok(new MessageResponse(messages.get("YEAR_DELETED")));
    }

    private User getUserByUserId(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("USER_NOT_FOUND")));
    }

    private Year getYearByYearIdAndUserId(Long id, Long userId) {
        return yearRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("YEAR_NOT_FOUND")));
    }
}
