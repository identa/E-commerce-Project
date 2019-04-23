package com.dac.spring.repository;

import com.dac.spring.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Integer> {
    boolean existsByEmployeeIdAndProductId(int id1, int id2);
    List<CartEntity> findAllByEmployeeId(int id);
}
