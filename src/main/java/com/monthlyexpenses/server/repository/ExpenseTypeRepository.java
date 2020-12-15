package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {

    List<ExpenseType> findAllByUserId(Long userId);

    Optional<ExpenseType> findByIdAndUserId(Long id, Long userId);
}
