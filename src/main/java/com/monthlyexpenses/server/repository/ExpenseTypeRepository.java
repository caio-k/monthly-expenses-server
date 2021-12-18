package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.ExpenseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseTypeRepository extends JpaRepository<ExpenseType, Long> {

    List<ExpenseType> findAllByCustomerIdOrderByNameAsc(Long customerId);

    Optional<ExpenseType> findByIdAndCustomerId(Long id, Long customerId);
}
