package com.dac.spring.repository;

import com.dac.spring.entity.ProductEntity;
import com.dac.spring.entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findByShop(ShopEntity shopEntity);
    List<ProductEntity> findByNameContaining(String name);
    List<ProductEntity> findByCategoryId(int id);
}
