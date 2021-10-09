package com.monthlyexpenses.server.app.usecase.year;

import com.monthlyexpenses.server.app.repository.YearRepository;
import com.monthlyexpenses.server.domain.Year;

public class YearDeleteService {

    private final YearRepository yearRepository;
    private final YearGetService yearGetService;

    public YearDeleteService(YearRepository yearRepository, YearGetService yearGetService) {
        this.yearRepository = yearRepository;
        this.yearGetService = yearGetService;
    }

    public void deleteYear(Long userId, Long yearId) {
        Year year = yearGetService.findYearByIdAndUserIdOrElseThrow(yearId, userId);
        yearRepository.delete(year);
    }
}
