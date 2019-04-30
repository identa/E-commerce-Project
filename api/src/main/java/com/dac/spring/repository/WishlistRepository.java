package com.dac.spring.repository;

import com.dac.spring.entity.WishlistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity, Integer> {
    List<WishlistEntity> findAllByEmployeeId(int id);
    WishlistEntity findByProductIdAndEmployeeId(int pid, int uid);
}
