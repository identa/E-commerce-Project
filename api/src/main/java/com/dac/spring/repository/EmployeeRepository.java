package com.dac.spring.repository;

import com.dac.spring.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
    EmployeeEntity findByEmailAndPassword(String email, String password);
    EmployeeEntity findByEmail(String email);
    List<EmployeeEntity> findByDeletedAndRoleId(boolean deleted, int id);
}
