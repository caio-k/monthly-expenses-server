package com.monthlyexpenses.server.repository;

import com.monthlyexpenses.server.model.ERole;
import com.monthlyexpenses.server.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);
}
