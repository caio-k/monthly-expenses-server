package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.User;
import com.monthlyexpenses.server.model.Year;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class YearRepositoryTest {

    @Autowired
    private YearRepository yearRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void save_Should_CreateYear_When_Successful() {
        Year yearToBeSaved = createYear1();
        Year yearSaved = yearRepository.save(yearToBeSaved);

        assertThat(yearSaved).isNotNull();
        assertThat(yearSaved.getId()).isNotNull();
        assertThat(yearSaved.getYearNumber()).isEqualTo(yearToBeSaved.getYearNumber());
    }

    @Test
    void save_Should_ThrowConstraintViolationException_When_SavingYearWithYearNumberNull() {
        Year yearToBeSaved = new Year();
        assertThatThrownBy(() -> yearRepository.save(yearToBeSaved))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void save_Should_UpdateYear_When_Successful() {
        Year yearToBeSaved = createYear1();
        Year yearSaved = yearRepository.save(yearToBeSaved);

        Integer newYearNumber = 2020;
        yearSaved.setYearNumber(newYearNumber);
        Year yearUpdated = yearRepository.save(yearSaved);

        assertThat(yearUpdated).isNotNull();
        assertThat(yearUpdated.getId()).isNotNull();
        assertThat(yearUpdated.getYearNumber()).isEqualTo(newYearNumber);
    }

    @Test
    void save_Should_ThrowConstraintViolationException_When_UpdatingYearWithYearNumberNull() {
        Year yearToBeSaved = createYear1();
        Year yearSaved = yearRepository.save(yearToBeSaved);

        yearSaved.setYearNumber(null);
        assertThatThrownBy(() -> yearRepository.saveAndFlush(yearSaved))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void delete_Should_DeleteYear_When_Successful() {
        Year yearToBeSaved = createYear1();
        Year yearSaved = yearRepository.save(yearToBeSaved);

        yearRepository.delete(yearSaved);
        Optional<Year> optionalYear = yearRepository.findById(yearSaved.getId());

        assertThat(optionalYear).isNotPresent();
    }

    @Test
    void findByIdAndUserId_Should_FindYear_When_Successful() {
        Year yearToBeSaved = createYear1();
        Year yearSaved = yearRepository.save(yearToBeSaved);

        Optional<Year> yearFound = yearRepository.findByIdAndUserId(yearSaved.getId(), yearSaved.getUser().getId());

        assertThat(yearFound).isPresent();
        assertThat(yearFound.get().getYearNumber()).isEqualTo(yearSaved.getYearNumber());
    }

    @Test
    void findByIdAndUserId_Should_NotFindAnyYear_When_YearIdAndUserIdIsNonexistent() {
        Year yearToBeSaved = createYear1();
        Year yearToBeSearched = createYear2();
        yearRepository.save(yearToBeSaved);

        //Nothing is found because 'yearToBeSearched' was not saved.
        Optional<Year> yearFound = yearRepository.findByIdAndUserId(yearToBeSearched.getId(), yearToBeSearched.getUser().getId());

        assertThat(yearFound).isNotPresent();
    }

    @Test
    void findByIdAndUserId_Should_NotFindAnyYear_When_YearIdIsNonexistent() {
        Year yearToBeSaved = createYear1();
        Year fakeYear = createYear2();
        Year yearSaved = yearRepository.save(yearToBeSaved);

        //Nothing is found because 'fakeYear.getId()' does not exist.
        Optional<Year> yearFound = yearRepository.findByIdAndUserId(fakeYear.getId(), yearSaved.getUser().getId());

        assertThat(yearFound).isNotPresent();
    }

    @Test
    void findByIdAndUserId_Should_NotFindAnyYear_When_UserIdIsNonexistent() {
        Year yearToBeSaved = createYear1();
        Year fakeYear = createYear2();
        Year yearSaved = yearRepository.save(yearToBeSaved);

        //Nothing is found because 'fakeYear.getUser().getId()' does not exist.
        Optional<Year> yearFound = yearRepository.findByIdAndUserId(yearSaved.getId(), fakeYear.getUser().getId());

        assertThat(yearFound).isNotPresent();
    }

    @Test
    void findByIdAndUserId_Should_NotFindAnyYear_When_PairOfIdAndUserIdIsNonexistent() {
        Year yearToBeSaved1 = createYear1();
        Year yearToBeSaved2 = createYear2();
        Year yearSaved1 = yearRepository.save(yearToBeSaved1);
        Year yearSaved2 = yearRepository.save(yearToBeSaved2);

        //Nothing is found because the pair 'yearSaved1.getId()' and 'yearSaved2.getUser().getId()' does not exist.
        Optional<Year> yearFound = yearRepository.findByIdAndUserId(yearSaved1.getId(), yearSaved2.getUser().getId());

        assertThat(yearFound).isNotPresent();
    }

    @Test
    void findByYearNumberAndUserId_Should_FindYear_When_Successful() {
        Year yearToBeSaved = createYear1();
        Year yearSaved = yearRepository.save(yearToBeSaved);

        Optional<Year> optionalYear = yearRepository.findByYearNumberAndUserId(yearSaved.getYearNumber(), yearSaved.getUser().getId());

        assertThat(optionalYear).isPresent();
        assertThat(optionalYear.get().getId()).isNotNull();
        assertThat(optionalYear.get().getYearNumber()).isEqualTo(yearSaved.getYearNumber());
    }

    @Test
    void findByYearNumberAndUserId_Should_NotFindAnyYear_When_YearNumberIsNonexistent() {
        Year yearToBeSaved = createYear1();
        Year fakeYear = createYear2();
        Year yearSaved = yearRepository.save(yearToBeSaved);

        //Nothing is found because 'fakeYear.getYearNumber()' does not exist.
        Optional<Year> optionalYear = yearRepository.findByYearNumberAndUserId(fakeYear.getYearNumber(), yearSaved.getUser().getId());

        assertThat(optionalYear).isNotPresent();
    }

    @Test
    void findByYearNumberAndUserId_Should_NotFindAnyYear_When_UserIdIsNonexistent() {
        Year yearToBeSaved = createYear1();
        Year fakeYear = createYear2();
        Year yearSaved = yearRepository.save(yearToBeSaved);

        //Nothing is found because 'fakeYear.getUser().getId()' does not exist.
        Optional<Year> optionalYear = yearRepository.findByYearNumberAndUserId(yearSaved.getYearNumber(), fakeYear.getUser().getId());

        assertThat(optionalYear).isNotPresent();
    }

    @Test
    void findByYearNumberAndUserId_Should_NotFindAnyYear_When_PairOfYearNumberAndUserIdIsNonexistent() {
        Year yearToBeSaved1 = createYear1();
        Year yearToBeSaved2 = createYear2();
        Year yearSaved1 = yearRepository.save(yearToBeSaved1);
        Year yearSaved2 = yearRepository.save(yearToBeSaved2);

        //Nothing is found because the pair 'yearSaved1.getYearNumber()' and 'yearSaved2.getUser().getId()' does not exist.
        Optional<Year> optionalYear = yearRepository.findByYearNumberAndUserId(yearSaved1.getYearNumber(), yearSaved2.getUser().getId());

        assertThat(optionalYear).isNotPresent();
    }

    @Test
    void findAllByUserIdOrderByYearNumberDesc_Should_FindAllYears_When_Successful() {
        List<Year> years = createTwoYearsForTheSameUser();
        Year yearSaved1 = yearRepository.save(years.get(0));
        Year yearSaved2 = yearRepository.save(years.get(1));

        List<Year> yearsFound = yearRepository.findAllByUserIdOrderByYearNumberDesc(years.get(0).getUser().getId());

        assertThat(yearsFound.size()).isEqualTo(2);
        assertThat(yearsFound.get(0).getId()).isNotNull();
        assertThat(yearsFound.get(1).getId()).isNotNull();
        assertThat(yearsFound.get(0).getYearNumber()).isEqualTo(yearSaved2.getYearNumber());
        assertThat(yearsFound.get(1).getYearNumber()).isEqualTo(yearSaved1.getYearNumber());
    }

    @Test
    void findAllByUserIdOrderByYearNumberDesc_Should_FindAllYears_When_ExitsYearsOfOtherUsers() {
        List<Year> years = createTwoYearsForTheSameUser();
        Year yearToBeSaved = createYear1();
        Year yearSaved1 = yearRepository.save(years.get(0));
        Year yearSaved2 = yearRepository.save(years.get(1));
        yearRepository.save(yearToBeSaved);

        List<Year> yearsFound = yearRepository.findAllByUserIdOrderByYearNumberDesc(years.get(0).getUser().getId());

        assertThat(yearsFound.size()).isEqualTo(2);
        assertThat(yearsFound.get(0).getId()).isNotNull();
        assertThat(yearsFound.get(1).getId()).isNotNull();
        assertThat(yearsFound.get(0).getYearNumber()).isEqualTo(yearSaved2.getYearNumber());
        assertThat(yearsFound.get(1).getYearNumber()).isEqualTo(yearSaved1.getYearNumber());
    }

    @Test
    void findAllByUserIdOrderByYearNumberDesc_Should_FindNoYears_When_YearIsNonexistent() {
        Year yearToBeNotSaved = createYear1();

        List<Year> yearsFound = yearRepository.findAllByUserIdOrderByYearNumberDesc(yearToBeNotSaved.getUser().getId());

        assertThat(yearsFound.size()).isEqualTo(0);
    }

    private Year createYear1() {
        User user = userRepository.save(new User("username1", "email1@monthlyexpenses.com", "password1"));
        return new Year(2021, user);
    }

    private Year createYear2() {
        User user = userRepository.save(new User("username2", "email2@monthlyexpenses.com", "password2"));
        return new Year(2020, user);
    }

    private List<Year> createTwoYearsForTheSameUser() {
        List<Year> years = new ArrayList<>();
        User user = userRepository.save(new User("username3", "email3@monthlyexpenses.com", "password3"));
        years.add(new Year(2020, user));
        years.add(new Year(2021, user));
        return years;
    }
}