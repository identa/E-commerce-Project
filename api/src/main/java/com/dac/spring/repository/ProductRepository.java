package com.dac.spring.repository;

import com.dac.spring.entity.ProductEntity;
import com.dac.spring.model.enums.StatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findByCategoryId(int id);
    ProductEntity findByIdAndDeletedAndStatusName(int id, boolean deleted, StatusName statusName);
    ProductEntity findByIdAndDeleted(int id, boolean deleted);
    List<ProductEntity> findAllByDeletedAndStatusNameOrderByViewDesc(boolean deleted, StatusName statusName);
    List<ProductEntity> findAllByDeletedAndStatusNameOrderByOrderedDesc(boolean deleted, StatusName statusName);
    List<ProductEntity> findTop8ByDeletedAndStatusNameOrderByViewDesc(boolean deleted, StatusName statusName);
    List<ProductEntity> findTop4ByDeletedAndStatusNameOrderByOrderedDesc(boolean deleted, StatusName statusName);
    List<ProductEntity> findAllByDeletedAndStatusNameAndNameContaining(boolean deleted, StatusName statusName, String query);
    List<ProductEntity> findByCategoryIdAndDeletedAndStatusName(int id, boolean deleted, StatusName statusName);
}
