package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.Month;
import com.monthlyexpenses.server.model.MonthYear;
import com.monthlyexpenses.server.model.User;
import com.monthlyexpenses.server.model.Year;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MonthYearRepositoryTest {

    @Autowired
    private MonthYearRepository monthYearRepository;

    @Autowired
    private MonthRepository monthRepository;

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void save_Should_CreateMonthYear_When_Successful() {
        MonthYear monthYearToBeSaved = createMonthYear();
        MonthYear monthYearSaved = monthYearRepository.save(monthYearToBeSaved);

        assertThat(monthYearSaved).isNotNull();
        assertThat(monthYearSaved.getId()).isNotNull();
        assertThat(monthYearSaved.getMonth().getMonthNumber()).isEqualTo(monthYearSaved.getMonth().getMonthNumber());
        assertThat(monthYearSaved.getYear().getYearNumber()).isEqualTo(monthYearSaved.getYear().getYearNumber());
    }

    @Test
    void findByMonthAndYear_Should_FindMonthYear_When_Successful() {
        MonthYear monthYearToBeSaved = createMonthYear();
        MonthYear monthYearSaved = monthYearRepository.save(monthYearToBeSaved);

        Optional<MonthYear> monthYearOptional =
                monthYearRepository.findByMonthAndYear(monthYearSaved.getMonth(), monthYearSaved.getYear());

        assertThat(monthYearOptional).isPresent();
        assertThat(monthYearOptional.get().getId()).isNotNull();
        assertThat(monthYearOptional.get().getMonth()).isEqualTo(monthYearSaved.getMonth());
        assertThat(monthYearOptional.get().getYear()).isEqualTo(monthYearSaved.getYear());
    }

    @Test
    void findByMonthAndYear_Should_NotFindAnyYear_When_MonthYearIsNonexistent() {
        MonthYear monthYearToBeSaved = createMonthYear();
        MonthYear monthYearSaved = monthYearRepository.save(monthYearToBeSaved);

        Month anotherMonth = saveMonth(9);

        Optional<MonthYear> monthYearOptional =
                monthYearRepository.findByMonthAndYear(anotherMonth, monthYearSaved.getYear());

        assertThat(monthYearOptional).isNotPresent();
    }

    @Test
    void delete_Should_DeleteMonthYear_When_Successful() {
        MonthYear monthYearToBeSaved = createMonthYear();
        MonthYear monthYearSaved = monthYearRepository.save(monthYearToBeSaved);

        monthYearRepository.delete(monthYearSaved);
        Optional<MonthYear> monthYearOptional =
                monthYearRepository.findByMonthAndYear(monthYearSaved.getMonth(), monthYearSaved.getYear());

        assertThat(monthYearOptional).isNotPresent();
    }

    private MonthYear createMonthYear() {
        Month monthSaved = saveMonth(5);

        User user = new User("username", "email@monthlyexpenses.com", "password");
        User userSaved = userRepository.save(user);

        Year year = new Year(2021, userSaved);
        Year yearSaved = yearRepository.save(year);

        return new MonthYear(monthSaved, yearSaved);
    }

    private Month saveMonth(Integer monthNumber) {
        Month month = new Month();
        month.setMonthNumber(monthNumber);
        return monthRepository.save(month);
    }
}