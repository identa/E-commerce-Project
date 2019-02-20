package com.dac.spring.repository;

import com.dac.spring.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Integer> {
    Optional<ShopEntity> findByEmailAndPassword(String email, String password);
}
