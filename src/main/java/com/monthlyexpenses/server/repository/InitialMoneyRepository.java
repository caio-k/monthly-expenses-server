package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.InitialMoney;
import com.monthlyexpenses.server.model.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InitialMoneyRepository extends JpaRepository<InitialMoney, Long> {

    List<InitialMoney> findAllByMonthYear_YearAndUserId(Year monthYear_year, Long user_id);

    Optional<InitialMoney> findByIdAndUserId(Long id, Long userId);
}
