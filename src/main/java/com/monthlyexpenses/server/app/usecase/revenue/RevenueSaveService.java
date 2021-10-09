package com.monthlyexpenses.server.app.usecase.revenue;

import com.monthlyexpenses.server.app.exception.ResourceAlreadyExistsException;
import com.monthlyexpenses.server.app.repository.RevenueRepository;
import com.monthlyexpenses.server.app.usecase.user.UserGetService;
import com.monthlyexpenses.server.app.usecase.year.YearGetService;
import com.monthlyexpenses.server.domain.Revenue;
import com.monthlyexpenses.server.domain.User;
import com.monthlyexpenses.server.domain.Year;
import com.monthlyexpenses.server.domain.enums.Month;

import java.util.Optional;

public class RevenueSaveService {

    private final RevenueRepository revenueRepository;
    private final RevenueGetService revenueGetService;
    private final YearGetService yearGetService;
    private final UserGetService userGetService;

    public RevenueSaveService(RevenueRepository revenueRepository, RevenueGetService revenueGetService,
                              YearGetService yearGetService, UserGetService userGetService) {
        this.revenueRepository = revenueRepository;
        this.revenueGetService = revenueGetService;
        this.yearGetService = yearGetService;
        this.userGetService = userGetService;
    }

    public Revenue createRevenue(Long userId, Integer yearNumber, Month month, Float value) {
        User user = userGetService.findUserByIdOrElseThrow(userId);
        Year year = yearGetService.findYearByNumberAndUserIdOrElseThrow(yearNumber, userId);
        Revenue revenue = new Revenue(user, value, month, year);

        validateRevenueExistence(month, year, user);
        return revenueRepository.save(revenue);
    }

    public Revenue updateRevenue(Long userId, Long revenueId, Float value) {
        Revenue revenue = revenueGetService.findRevenueByIdAndUserIdOrElseThrow(revenueId, userId);
        revenue.setValue(value);
        return revenueRepository.save(revenue);
    }

    private void validateRevenueExistence(Month month, Year year, User user) {
        Optional<Revenue> revenueOptional = revenueGetService.findRevenueByMonthAndYearIdAndUserId(month, year.getId(), user.getId());

        if (revenueOptional.isPresent()) {
            throw new ResourceAlreadyExistsException("Já foi cadastrada uma receita para o ano/mês solicitado");
        }
    }
}
