package com.monthlyexpenses.server.app.usecase.year;

import com.monthlyexpenses.server.app.exception.ResourceNotFoundException;
import com.monthlyexpenses.server.app.repository.YearRepository;
import com.monthlyexpenses.server.domain.Year;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import static java.util.Calendar.YEAR;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class YearGetService {

    private final YearRepository yearRepository;

    public YearGetService(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    public Optional<Year> findYearByNumberAndUserId(Integer number, Long userId) {
        return yearRepository.findYearByNumberAndUserId(number, userId);
    }

    public Year findYearByIdAndUserIdOrElseThrow(Long id, Long userId) {
        return yearRepository.findYearByIdAndUserId(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível encontrar o ano desejado."));
    }

    public List<Year> findAllYearsByUserId(Long userId) {
        return yearRepository.findAllByUserIdOrderByYearNumberDesc(userId);
    }

    public Optional<Year> findNearestYearFromNow(Long userId) {
        int index = 0;
        Integer actualYear = GregorianCalendar.getInstance().get(YEAR);

        List<Year> years = yearRepository.findAllByUserIdOrderByYearNumberDesc(userId);

        if (years.isEmpty()) return empty();

        while (index < years.size() && years.get(index).getNumber().compareTo(actualYear) > 0) {
            index++;
        }

        if (index < years.size()) {
            if (index > 0 && years.get(index).getNumber().compareTo(actualYear) < 0) {
                index--;
            }
        } else {
            index--;
        }

        return of(years.get(index));
    }
}
