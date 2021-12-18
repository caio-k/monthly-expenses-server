package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.constants.Month;
import com.monthlyexpenses.server.model.InitialMoney;
import com.monthlyexpenses.server.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InitialMoneyRepository extends JpaRepository<InitialMoney, Long> {

    Optional<InitialMoney> findByIdAndCustomerId(Long id, Long customerId);

    Optional<InitialMoney> findByMonthAndYearAndCustomerId(Month month, Year year, Long customerId);
}
