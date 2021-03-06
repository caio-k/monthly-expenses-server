package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {

    List<ExpenseType> findAllByUserIdOrderByNameAsc(Long userId);

    Optional<ExpenseType> findByIdAndUserId(Long id, Long userId);
}
