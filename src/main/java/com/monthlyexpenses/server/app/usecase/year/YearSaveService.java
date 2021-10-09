package com.monthlyexpenses.server.app.usecase.year;

import com.monthlyexpenses.server.app.exception.ResourceAlreadyExistsException;
import com.monthlyexpenses.server.app.repository.YearRepository;
import com.monthlyexpenses.server.app.usecase.user.UserGetService;
import com.monthlyexpenses.server.domain.User;
import com.monthlyexpenses.server.domain.Year;

import java.util.Optional;

import static java.lang.String.format;

public class YearSaveService {

    private final YearRepository yearRepository;
    private final YearGetService yearGetService;
    private final UserGetService userGetService;

    public YearSaveService(YearRepository yearRepository, YearGetService yearGetService, UserGetService userGetService) {
        this.yearRepository = yearRepository;
        this.yearGetService = yearGetService;
        this.userGetService = userGetService;
    }

    public Year createYear(Long userId, Integer yearNumber) {
        User user = userGetService.findUserByIdOrElseThrow(userId);
        Year year = new Year(yearNumber, user);

        validateYearExistence(yearNumber, userId);

        return yearRepository.save(year);
    }

    public Year updateYear(Long userId, Long yearId, Integer newYearNumber) {
        Year year = yearGetService.findYearByIdAndUserIdOrElseThrow(yearId, userId);
        year.setNumber(newYearNumber);

        validateYearExistence(newYearNumber, userId);

        return yearRepository.save(year);
    }

    private void validateYearExistence(Integer yearNumber, Long userId) {
        Optional<Year> yearOptional = yearGetService.findYearByNumberAndUserId(yearNumber, userId);

        if (yearOptional.isPresent()) {
            throw new ResourceAlreadyExistsException(format("O ano %d já foi cadastrado.", yearNumber));
        }
    }
}
