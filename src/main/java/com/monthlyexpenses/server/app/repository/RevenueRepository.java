package com.monthlyexpenses.server.app.repository;

import com.monthlyexpenses.server.domain.Revenue;
import com.monthlyexpenses.server.domain.enums.Month;

import java.util.Optional;

public interface RevenueRepository {

    Revenue save(Revenue revenue);

    Optional<Revenue> findRevenueByMonthAndYearIdAndUserId(Month month, Long yearId, Long userId);

    Optional<Revenue> findRevenueByIdAndUserId(Long revenueId, Long userId);
}
