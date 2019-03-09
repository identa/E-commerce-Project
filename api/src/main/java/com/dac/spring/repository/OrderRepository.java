package com.dac.spring.repository;

import com.dac.spring.entity.OrderEntity;
import com.dac.spring.model.enums.StatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    OrderEntity findByIdAndDeletedAndStatusName(int id, boolean deleted, StatusName statusName);
    OrderEntity findByIdAndDeleted(int id, boolean deleted);
}
