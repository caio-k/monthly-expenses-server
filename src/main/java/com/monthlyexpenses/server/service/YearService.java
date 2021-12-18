package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import com.monthlyexpenses.server.exceptions.ResourceNotFoundException;
import com.monthlyexpenses.server.exceptions.UniqueViolationException;
import com.monthlyexpenses.server.configuration.MessagesComponent;
import com.monthlyexpenses.server.model.Month;
import com.monthlyexpenses.server.model.MonthYear;
import com.monthlyexpenses.server.model.User;
import com.monthlyexpenses.server.model.Year;
import com.monthlyexpenses.server.repository.MonthYearRepository;
import com.monthlyexpenses.server.repository.YearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class YearService {

    private final YearRepository yearRepository;
    private final MonthYearRepository monthYearRepository;
    private final UserService userService;
    private final MonthService monthService;
    private final MessagesComponent messages;

    public List<YearResponse> getAllYearsByUserId(Long userId) {
        return yearRepository.findAllByUserIdOrderByYearNumberDesc(userId)
                .stream().map(this::buildYearResponse)
                .collect(Collectors.toList());
    }

    public YearResponse createYear(Long userId, Integer yearNumber) {
        User user = userService.getUserByUserId(userId);
        Year year = new Year(yearNumber, user);

        try {
            Year yearSaved = yearRepository.saveAndFlush(year);
            List<Month> months = monthService.findAll();
            months.forEach(month -> monthYearRepository.saveAndFlush(new MonthYear(month, yearSaved)));
            return buildYearResponse(yearSaved);
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("YEAR_ALREADY_EXISTS"));
        }
    }

    public YearResponse updateYear(Long userId, Long yearId, Integer yearNumber) {
        Year year = findYearByYearIdAndUserId(yearId, userId);

        try {
            year.setYearNumber(yearNumber);
            Year yearSaved = yearRepository.saveAndFlush(year);
            return buildYearResponse(yearSaved);
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("YEAR_ALREADY_EXISTS"));
        }
    }

    public MessageResponse deleteYear(Long userId, Long yearId) {
        Year year = findYearByYearIdAndUserId(yearId, userId);
        yearRepository.delete(year);
        return MessageResponse.builder().message(messages.get("YEAR_DELETED")).build();
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

    private YearResponse buildYearResponse(Year year) {
        return YearResponse.builder()
                .id(year.getId())
                .yearNumber(year.getYearNumber())
                .build();
    }
}
