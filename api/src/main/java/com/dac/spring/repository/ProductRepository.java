package com.dac.spring.repository;

import com.dac.spring.entity.ProductEntity;
import com.dac.spring.model.enums.StatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findByNameContaining(String name);
    List<ProductEntity> findByCategoryId(int id);
    ProductEntity findByIdAndDeletedAndStatusName(int id, boolean deleted, StatusName statusName);
    ProductEntity findByIdAndDeleted(int id, boolean deleted);
}
