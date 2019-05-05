package com.dac.spring.repository;

import com.dac.spring.entity.EmployeeEntity;
import com.dac.spring.entity.EmployeeInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeInfoRepository extends JpaRepository<EmployeeInfoEntity, Integer> {
    boolean existsByEmployee(EmployeeEntity employeeEntity);
    EmployeeInfoEntity findByEmployeeId(int id);

}
