package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.Month;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class MonthRepositoryTest {

    @Autowired
    private MonthRepository monthRepository;

    @Test
    void save_Should_CreateMonth_When_Successful() {
        Month monthToBeSaved = createMonth(5);
        Month monthSaved = monthRepository.save(monthToBeSaved);

        assertThat(monthSaved).isNotNull();
        assertThat(monthSaved.getMonthNumber()).isEqualTo(monthToBeSaved.getMonthNumber());
    }

    @Test
    void save_Should_ThrowDataIntegrityViolationException_When_CreatingMonthWithMonthNumberExistent() {
        Month monthToBeSaved1 = createMonth(5);
        Month monthToBeSaved2 = createMonth(5);
        monthRepository.save(monthToBeSaved1);

        assertThatThrownBy(() -> monthRepository.save(monthToBeSaved2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void save_Should_ThrowConstraintViolationException_When_CreatingMonthWithMonthNumberNull() {
        Month monthToBeSaved = createMonth(null);

        assertThatThrownBy(() -> monthRepository.save(monthToBeSaved))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void save_Should_UpdateMonth_When_Successful() {
        Month monthToBeSaved = createMonth(5);
        Month monthSaved = monthRepository.save(monthToBeSaved);

        monthSaved.setMonthNumber(8);
        Month monthUpdated = monthRepository.save(monthSaved);

        assertThat(monthUpdated).isNotNull();
        assertThat(monthUpdated.getId()).isNotNull();
        assertThat(monthUpdated.getMonthNumber()).isEqualTo(monthSaved.getMonthNumber());
    }

    @Test
    void save_Should_ThrowDataIntegrityViolationException_When_UpdatingMonthWithMonthNumberExistent() {
        Month monthToBeSaved1 = createMonth(5);
        Month monthToBeSaved2 = createMonth(9);
        Month monthSaved1 = monthRepository.save(monthToBeSaved1);
        monthRepository.save(monthToBeSaved2);

        monthSaved1.setMonthNumber(9);

        assertThatThrownBy(() -> monthRepository.saveAndFlush(monthSaved1))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void save_Should_ThrowConstraintViolationException_When_UpdatingMonthWithMonthNumberNull() {
        Month monthToBeSaved = createMonth(5);
        Month monthSaved = monthRepository.save(monthToBeSaved);

        monthSaved.setMonthNumber(null);

        assertThatThrownBy(() -> monthRepository.saveAndFlush(monthSaved))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void delete_Should_DeleteMonth_When_Successful() {
        Month monthToBeSaved = createMonth(5);
        Month monthSaved = monthRepository.save(monthToBeSaved);

        monthRepository.delete(monthSaved);
        Optional<Month> optionalMonth = monthRepository.findById(monthSaved.getId());

        assertThat(optionalMonth).isNotPresent();
    }

    @Test
    void findByMonthNumber_Should_FindMonth_When_Successful() {
        Month monthToBeSaved = createMonth(5);
        Month monthSaved = monthRepository.save(monthToBeSaved);

        Optional<Month> optionalMonth = monthRepository.findByMonthNumber(monthSaved.getMonthNumber());

        assertThat(optionalMonth).isPresent();
        assertThat(optionalMonth.get().getMonthNumber()).isEqualTo(monthSaved.getMonthNumber());
    }

    @Test
    void findByMonthNumber_Should_NotFindAnyMonth_When_MonthNumberIsNonexistent() {
        Month monthToBeSaved = createMonth(5);
        monthRepository.save(monthToBeSaved);
        Integer monthNumberToBeSearched = 9;

        Optional<Month> optionalMonth = monthRepository.findByMonthNumber(monthNumberToBeSearched);

        assertThat(optionalMonth).isNotPresent();
    }

    private Month createMonth(Integer monthNumber) {
        Month month = new Month();
        month.setMonthNumber(monthNumber);
        return month;
    }
}