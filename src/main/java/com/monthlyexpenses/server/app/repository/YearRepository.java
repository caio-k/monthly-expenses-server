package com.monthlyexpenses.server.app.repository;

import com.monthlyexpenses.server.domain.User;
import com.monthlyexpenses.server.domain.Year;

import java.util.Optional;

public interface YearRepository {

    Year save(Year year);

    Optional<Year> findYearByNumberAndUser(Integer yearNumber, User user);
}
