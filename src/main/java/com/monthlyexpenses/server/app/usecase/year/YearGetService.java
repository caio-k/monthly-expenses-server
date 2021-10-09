package com.monthlyexpenses.server.app.usecase.year;

import com.monthlyexpenses.server.app.repository.YearRepository;
import com.monthlyexpenses.server.domain.User;
import com.monthlyexpenses.server.domain.Year;

import java.util.Optional;

public class YearGetService {

    private final YearRepository yearRepository;

    public YearGetService(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }

    public Optional<Year> findYearByNumberAndUser(Integer number, User user) {
        return yearRepository.findYearByNumberAndUser(number, user);
    }
}
