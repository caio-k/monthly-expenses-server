package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.InitialMoney;
import com.monthlyexpenses.server.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InitialMoneyRepository extends JpaRepository<InitialMoney, Long> {

    Optional<InitialMoney> findByIdAndCustomerId(Long id, Long customerId);

    Optional<InitialMoney> findByMonthNumberAndYearAndCustomerId(Integer month, Year year, Long customerId);
}
