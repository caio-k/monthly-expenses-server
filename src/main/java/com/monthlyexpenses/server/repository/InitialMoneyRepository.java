package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.InitialMoney;
import com.monthlyexpenses.server.model.Month;
import com.monthlyexpenses.server.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InitialMoneyRepository extends JpaRepository<InitialMoney, Long> {

    Optional<InitialMoney> findByIdAndUserId(Long id, Long userId);

    Optional<InitialMoney> findByMonthYear_MonthAndMonthYear_YearAndUserId(Month monthYear_month, Year monthYear_year, Long user_id);
}
