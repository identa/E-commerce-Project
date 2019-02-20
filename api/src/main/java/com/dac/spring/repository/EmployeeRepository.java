package com.example.boot_demo.repository;

import com.example.boot_demo.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
    EmployeeEntity findByEmailAndPassword(String email, String password);
    EmployeeEntity findByEmail(String email);
    List<EmployeeEntity> findByDeletedAndRoleId(boolean deleted, int id);
}
