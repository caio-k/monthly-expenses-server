package com.monthlyexpenses.server.service;

import com.monthlyexpenses.server.BasicConfigurationTest;
import com.monthlyexpenses.server.configuration.MessagesComponent;
import com.monthlyexpenses.server.dto.response.year.YearResponse;
import com.monthlyexpenses.server.exceptions.ResourceNotFoundException;
import com.monthlyexpenses.server.exceptions.UniqueViolationException;
import com.monthlyexpenses.server.model.Customer;
import com.monthlyexpenses.server.model.Year;
import com.monthlyexpenses.server.repository.YearRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
    @DisplayName("Tests for findAllYearsByCustomerId method")
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
            when(yearRepository.findAllByCustomerIdOrderByYearNumberDesc(eq(1L)))
                    .thenReturn(List.of(yearWithNumber(2022)));

            List<YearResponse> yearsFound = yearService.findAllYearsByCustomerId(1L);

            assertEquals(1, yearsFound.size());
            assertEquals(1L, yearsFound.get(0).getId());
            assertEquals(2022, yearsFound.get(0).getYearNumber());
        }

        @Test
        void shouldFindAListWithTwoYears() {
            when(yearRepository.findAllByCustomerIdOrderByYearNumberDesc(eq(1L)))
                    .thenReturn(List.of(yearWithNumberAndId(2022, 2L), yearWithNumberAndId(2021, 1L)));

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

    @Nested
    @DisplayName("Tests for createYear method")
    class CreateYearTest {

        @Test
        void shouldThrowResourceNotFoundExceptionWhenNotFindCustomer() {
            when(customerService.findCustomerByIdOrElseThrow(1L))
                    .thenThrow(ResourceNotFoundException.class);

            assertThrows(ResourceNotFoundException.class, () -> yearService.createYear(1L, 2022));
        }

        @Test
        void shouldThrowUniqueViolationExceptionWhenYearNumberAlreadyExistsForUser() {
            when(customerService.findCustomerByIdOrElseThrow(1L))
                    .thenReturn(customer());

            when(yearRepository.saveAndFlush(any(Year.class)))
                    .thenThrow(DataIntegrityViolationException.class);

            assertThrows(UniqueViolationException.class, () -> yearService.createYear(1L, 2022));
        }

        @Test
        void shouldCreateYear() {
            when(customerService.findCustomerByIdOrElseThrow(1L))
                    .thenReturn(customer());

            when(yearRepository.saveAndFlush(any(Year.class)))
                    .thenReturn(yearWithNumber(2022));

            YearResponse yearResponse = yearService.createYear(1L, 2022);

            assertEquals(1L, yearResponse.getId());
            assertEquals(2022, yearResponse.getYearNumber());
        }
    }

    @Nested
    @DisplayName("Tests for updateYear method")
    class UpdateYearTest {

        @Test
        void shouldThrowResourceNotFoundExceptionWhenNotFindYear() {
            when(yearRepository.findByIdAndCustomerId(1L, 1L))
                    .thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> yearService.updateYear(1L, 1L, 2022));
        }

        @Test
        void shouldThrowUniqueViolationExceptionWhenYearNumberAlreadyExistsForUser() {
            when(yearRepository.findByIdAndCustomerId(1L, 1L))
                    .thenReturn(Optional.of(yearWithNumber(2023)));

            when(yearRepository.saveAndFlush(any(Year.class)))
                    .thenThrow(DataIntegrityViolationException.class);

            assertThrows(UniqueViolationException.class, () -> yearService.updateYear(1L, 1L, 2022));
        }

        @Test
        void shouldUpdateYear() {
            when(yearRepository.findByIdAndCustomerId(1L, 1L))
                    .thenReturn(Optional.of(yearWithNumber(2023)));

            when(yearRepository.saveAndFlush(any(Year.class)))
                    .thenReturn(yearWithNumber(2022));

            YearResponse yearResponse = yearService.updateYear(1L, 1L, 2022);

            assertEquals(1L, yearResponse.getId());
            assertEquals(2022, yearResponse.getYearNumber());
        }
    }

    @Nested
    @DisplayName("Tests for findYearByIdAndCustomerIdOrElseThrow method")
    class FindYearByIdAndCustomerIdOrElseThrowTest {

        @Test
        void shouldNotFindYearByIdAndCustomerIdAndThrowsResourceNotFoundException() {
            when(yearRepository.findByIdAndCustomerId(1L, 1L))
                    .thenReturn(Optional.empty());

            assertThrows(ResourceNotFoundException.class, () -> yearService.findYearByIdAndCustomerIdOrElseThrow(1L, 1L));
        }

        @Test
        void shouldFindYearByIdAndCustomer() {
            when(yearRepository.findByIdAndCustomerId(1L, 1L))
                    .thenReturn(Optional.of(yearWithNumber(2022)));

            Year year = yearService.findYearByIdAndCustomerIdOrElseThrow(1L, 1L);

            assertEquals(1L, year.getId());
            assertEquals(2022, year.getYearNumber());
            assertEquals(1L, year.getCustomer().getId());
        }
    }

    @Nested
    @DisplayName("Tests for findTheSmallestYearGreaterThanTheCurrentYear method")
    class FindTheSmallestYearGreaterThanTheCurrentYear {

        @Test
        void shouldReturnOptionalEmptyWhenNotFoundAnyYearInDatabase() {
            when(yearRepository.findAllByCustomerIdOrderByYearNumberDesc(1L))
                    .thenReturn(Collections.emptyList());

            Optional<Year> nearestYearFromNow = yearService.findTheSmallestYearGreaterThanTheCurrentYearOrElseTheGreatestYearSmallerThanTheCurrentYear(1L);

            assertTrue(nearestYearFromNow.isEmpty());
        }

        @ParameterizedTest
        @ValueSource(ints = {2021, 2022, 2023})
        void shouldFindOneYearInDatabaseAndReturnIt(int yearNumberInDatabase) {
            Calendar calendar = Mockito.mock(Calendar.class);

            when(calendar.get(Calendar.YEAR)).thenReturn(2022);

            when(yearRepository.findAllByCustomerIdOrderByYearNumberDesc(1L))
                    .thenReturn(List.of(yearWithNumber(yearNumberInDatabase)));

            yearService.calendar = calendar;

            Optional<Year> nearestYearFromNow = yearService.findTheSmallestYearGreaterThanTheCurrentYearOrElseTheGreatestYearSmallerThanTheCurrentYear(1L);

            assertTrue(nearestYearFromNow.isPresent());
            assertEquals(yearNumberInDatabase, nearestYearFromNow.get().getYearNumber());
        }
    }

    @ParameterizedTest
    @MethodSource("provideTwoYearsNumberAndExpectedResultWhenCurrentYearIs2022")
    void shouldFindTwoYearsAndReturnTheNearestYear(int yearNumber1, int yearNumber2, int nearestYearExpected, String details) {
        Calendar calendar = Mockito.mock(Calendar.class);

        when(calendar.get(Calendar.YEAR)).thenReturn(2022);

        when(yearRepository.findAllByCustomerIdOrderByYearNumberDesc(1L))
                .thenReturn(List.of(yearWithNumber(yearNumber1), yearWithNumber(yearNumber2)));

        yearService.calendar = calendar;

        Optional<Year> nearestYearFromNow = yearService.findTheSmallestYearGreaterThanTheCurrentYearOrElseTheGreatestYearSmallerThanTheCurrentYear(1L);

        assertTrue(nearestYearFromNow.isPresent(), details);
        assertEquals(nearestYearExpected, nearestYearFromNow.get().getYearNumber(), details);
    }

    private static Stream<Arguments> provideTwoYearsNumberAndExpectedResultWhenCurrentYearIs2022() {
        return Stream.of(
                Arguments.of(2021, 2020, 2021, "should find two years smaller than the current year and return the greatest year between them"),
                Arguments.of(2024, 2023, 2023, "should find two years greater than the current year and return the smallest year between them"),
                Arguments.of(2023, 2021, 2023, "should find one year smaller and another year greater than the current year return the greatest year between them"),
                Arguments.of(2022, 2021, 2022, "should find one year smaller than the current year and another year equals to current year and return the current year"),
                Arguments.of(2023, 2022, 2022, "should find one year greater than the current year and another year equals to current year and return the current year")
        );
    }

    @ParameterizedTest
    @MethodSource("provideThreeYearsNumberAndExpectedResultWhenCurrentYearIs2022")
    void shouldFindThreeYearsAndReturnTheNearestYear(int yearNumber1, int yearNumber2, int yearNumber3, int nearestYearExpected, String details) {
        Calendar calendar = Mockito.mock(Calendar.class);

        when(calendar.get(Calendar.YEAR)).thenReturn(2022);

        when(yearRepository.findAllByCustomerIdOrderByYearNumberDesc(1L))
                .thenReturn(List.of(yearWithNumber(yearNumber1), yearWithNumber(yearNumber2), yearWithNumber(yearNumber3)));

        yearService.calendar = calendar;

        Optional<Year> nearestYearFromNow = yearService.findTheSmallestYearGreaterThanTheCurrentYearOrElseTheGreatestYearSmallerThanTheCurrentYear(1L);

        assertTrue(nearestYearFromNow.isPresent(), details);
        assertEquals(nearestYearExpected, nearestYearFromNow.get().getYearNumber(), details);
    }

    private static Stream<Arguments> provideThreeYearsNumberAndExpectedResultWhenCurrentYearIs2022() {
        return Stream.of(
                Arguments.of(2021, 2020, 2019, 2021, "should find three years smaller than the current year and return the greatest year between them"),
                Arguments.of(2025, 2024, 2023, 2023, "should find three years greater than the current year and return the smallest year between them"),
                Arguments.of(2023, 2021, 2020, 2023, "should two one years smaller and another year greater than the current year return the greatest year between them"),
                Arguments.of(2022, 2021, 2020, 2022, "should find two years smaller than the current year and another year equals to current year and return the current year"),
                Arguments.of(2024, 2023, 2022, 2022, "should find two years greater than the current year and another year equals to current year and return the current year")
        );
    }

    private Customer customer() {
        return Customer.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .password("6g46er")
                .build();
    }

    private Year yearWithNumber(int yearNumber) {
        return yearWithNumberAndId(yearNumber, 1L);
    }

    private Year yearWithNumberAndId(int yearNumber, Long id) {
        return Year.builder()
                .id(id)
                .yearNumber(yearNumber)
                .customer(customer())
                .build();
    }
}