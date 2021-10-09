package com.monthlyexpenses.server.app.usecase.revenue;

import com.monthlyexpenses.server.app.exception.ResourceNotFoundException;
import com.monthlyexpenses.server.app.repository.RevenueRepository;
import com.monthlyexpenses.server.domain.Revenue;
import com.monthlyexpenses.server.domain.enums.Month;

import java.util.Optional;

public class RevenueGetService {

    private final RevenueRepository revenueRepository;

    public RevenueGetService(RevenueRepository revenueRepository) {
        this.revenueRepository = revenueRepository;
    }

    public Optional<Revenue> findRevenueByMonthAndYearIdAndUserId(Month month, Long yearId, Long userId) {
        return revenueRepository.findRevenueByMonthAndYearIdAndUserId(month, yearId, userId);
    }

    public Revenue findRevenueByIdAndUserIdOrElseThrow(Long revenueId, Long userId) {
        return revenueRepository.findRevenueByIdAndUserId(revenueId, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Não foi possível encontrar a receita a partir do identificador."));
    }
}
