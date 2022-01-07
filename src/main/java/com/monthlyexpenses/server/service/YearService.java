package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.configuration.MessagesComponent;
import com.monthlyexpenses.server.dto.response.MessageResponse;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import com.monthlyexpenses.server.exceptions.ResourceNotFoundException;
import com.monthlyexpenses.server.exceptions.UniqueViolationException;
import com.monthlyexpenses.server.model.Customer;
import com.monthlyexpenses.server.model.Year;
import com.monthlyexpenses.server.repository.YearRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class YearService {

    private final YearRepository yearRepository;
    private final CustomerService customerService;
    private final MessagesComponent messages;

    Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);

    public List<YearResponse> findAllYearsByCustomerId(Long customerId) {
        return yearRepository.findAllByCustomerIdOrderByYearNumberDesc(customerId)
                .stream().map(this::buildYearResponse)
                .collect(Collectors.toList());
    }

    public YearResponse createYear(Long customerId, Integer yearNumber) {
        Customer customer = customerService.findCustomerByIdOrElseThrow(customerId);
        Year year = Year.builder()
                .yearNumber(yearNumber)
                .customer(customer)
                .build();

        return saveYearAndBuildResponse(year);
    }

    public YearResponse updateYear(Long customerId, Long yearId, Integer yearNumber) {
        Year year = findYearByIdAndCustomerIdOrElseThrow(yearId, customerId);
        year.setYearNumber(yearNumber);

        return saveYearAndBuildResponse(year);
    }

    public MessageResponse deleteYear(Long customerId, Long yearId) {
        Year year = findYearByIdAndCustomerIdOrElseThrow(yearId, customerId);
        yearRepository.delete(year);
        return MessageResponse.builder().message(messages.get("YEAR_DELETED")).build();
    }

    public Optional<Year> findTheSmallestYearGreaterThanTheCurrentYearOrElseTheGreatestYearSmallerThanTheCurrentYear(Long customerId) {
        List<Year> years = yearRepository.findAllByCustomerIdOrderByYearNumberDesc(customerId);
        if (years.isEmpty()) return Optional.empty();

        int index = 0;

        while (index < years.size() && isGreaterThanTheCurrentYear(years.get(index).getYearNumber())) {
            index++;
        }

        if (index < years.size()) {
            if (index > 0 && isSmallerThanTheCurrentYear(years.get(index).getYearNumber())) {
                index--;
            }
        } else {
            index--;
        }

        return Optional.of(years.get(index));
    }

    public Year findYearByIdAndCustomerIdOrElseThrow(Long id, Long customerId) {
        return yearRepository.findByIdAndCustomerId(id, customerId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("YEAR_NOT_FOUND")));
    }

    public Year findYearByNumberAndCustomerIdOrElseThrow(Integer yearNumber, Long userId) {
        return yearRepository.findByYearNumberAndCustomerId(yearNumber, userId)
                .orElseThrow(() -> new ResourceNotFoundException(messages.get("YEAR_NOT_FOUND")));
    }

    private YearResponse saveYearAndBuildResponse(Year year) {
        try {
            Year yearSaved = yearRepository.saveAndFlush(year);
            return buildYearResponse(yearSaved);
        } catch (DataIntegrityViolationException exception) {
            throw new UniqueViolationException(messages.get("YEAR_ALREADY_EXISTS"));
        }
    }

    private YearResponse buildYearResponse(Year year) {
        return YearResponse.builder()
                .id(year.getId())
                .yearNumber(year.getYearNumber())
                .build();
    }

    private boolean isGreaterThanTheCurrentYear(Integer yearNumber) {
        return yearNumber.compareTo(currentYear) > 0;
    }

    private boolean isSmallerThanTheCurrentYear(Integer yearNumber) {
        return yearNumber.compareTo(currentYear) < 0;
    }
}
