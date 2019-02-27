package com.dac.spring.repository;

import com.dac.spring.entity.StatusEntity;
import com.dac.spring.model.enums.StatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
    StatusEntity findByName(StatusName statusName);
    boolean existsByName(StatusName statusName);
}
