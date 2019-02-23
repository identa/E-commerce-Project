package com.dac.spring.repository;

import com.dac.spring.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
    Optional<EmployeeEntity> findByEmailAndPassword(String email, String password);
    EmployeeEntity findByEmail(String email);
    List<EmployeeEntity> findByDeletedAndRoleId(boolean deleted, int id);
    boolean existsByEmail(String email);
}