package com.example.boot_demo.repository;

import com.example.boot_demo.entity.OrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetailEntity, Integer> {
}
