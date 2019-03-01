package com.dac.spring.repository;

import com.dac.spring.entity.EmployeeEntity;
import com.dac.spring.model.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
    Optional<EmployeeEntity> findByEmail(String email);
    List<EmployeeEntity> findByDeletedAndRoleName(boolean deleted, RoleName roleName);
    boolean existsByEmail(String email);
    EmployeeEntity findById(int id);
    boolean existsByIdAndRoleName(int id, RoleName roleName);
    Optional<EmployeeEntity> findByIdAndDeletedAndRoleNameOrRoleName(int id, boolean deleted, RoleName roleName1, RoleName roleName2);
    boolean existsByEmailAndDeleted(String email, boolean deleted);
    Optional<EmployeeEntity> findByEmailAndDeleted(String email, boolean deleted);
    Optional<EmployeeEntity> findByIdAndDeletedAndRoleName(int id, boolean deleted, RoleName roleName);
    Optional<EmployeeEntity> findByIdAndDeleted(int id, boolean deleted);
}