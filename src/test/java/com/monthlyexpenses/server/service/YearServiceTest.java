package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.BasicConfigurationTest;
import com.monthlyexpenses.server.configuration.MessagesComponent;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import com.monthlyexpenses.server.model.Year;
import com.monthlyexpenses.server.repository.YearRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class YearServiceTest extends BasicConfigurationTest {

    @InjectMocks
    private YearService yearService;

    @Mock
    private YearRepository yearRepository;

    @Mock
    private CustomerService customerService;

    @Mock
    private MessagesComponent messages;

    @Nested
    class FindAllYearsByCustomerIdTest {

        @Test
        void shouldFindAEmptyList() {
            when(yearRepository.findAllByCustomerIdOrderByYearNumberDesc(eq(1L)))
                    .thenReturn(emptyList());

            List<YearResponse> yearsFound = yearService.findAllYearsByCustomerId(1L);

            assertTrue(yearsFound.isEmpty());
        }

        @Test
        void shouldFindAListWithOneYear() {
            Year year = Year.builder()
                    .id(1L)
                    .yearNumber(2022)
                    .build();

            when(yearRepository.findAllByCustomerIdOrderByYearNumberDesc(eq(1L)))
                    .thenReturn(List.of(year));

            List<YearResponse> yearsFound = yearService.findAllYearsByCustomerId(1L);

            assertEquals(1, yearsFound.size());
            assertEquals(1L, yearsFound.get(0).getId());
            assertEquals(2022, yearsFound.get(0).getYearNumber());
        }

        @Test
        void shouldFindAListWithTwoYears() {
            Year year1 = Year.builder()
                    .id(1L)
                    .yearNumber(2021)
                    .build();

            Year year2 = Year.builder()
                    .id(2L)
                    .yearNumber(2022)
                    .build();

            when(yearRepository.findAllByCustomerIdOrderByYearNumberDesc(eq(1L)))
                    .thenReturn(List.of(year2, year1));

            List<YearResponse> yearsFound = yearService.findAllYearsByCustomerId(1L);

            assertEquals(2, yearsFound.size());
            assertEquals(2L, yearsFound.get(0).getId());
            assertEquals(2022, yearsFound.get(0).getYearNumber());
            assertEquals(1L, yearsFound.get(1).getId());
            assertEquals(2021, yearsFound.get(1).getYearNumber());
        }

        @Test
        void shouldThrowsExceptionWhenSearchInDatabase() {
            when(yearRepository.findAllByCustomerIdOrderByYearNumberDesc(eq(1L)))
                    .thenThrow(new RuntimeException());

            assertThrows(RuntimeException.class, () -> yearService.findAllYearsByCustomerId(1L));
        }
    }
}