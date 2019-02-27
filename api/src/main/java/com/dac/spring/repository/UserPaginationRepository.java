package com.dac.spring.repository;

import com.dac.spring.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaginationRepository extends PagingAndSortingRepository<EmployeeEntity, Integer> {
    Page<EmployeeEntity> findAll(Pageable pageable);
}
