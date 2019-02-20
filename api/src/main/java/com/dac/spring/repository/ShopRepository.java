package com.example.boot_demo.repository;

import com.example.boot_demo.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, Integer> {
    Optional<ShopEntity> findByEmailAndPassword(String email, String password);
}
