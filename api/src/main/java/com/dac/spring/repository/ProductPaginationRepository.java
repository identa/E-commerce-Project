package com.dac.spring.repository;

import com.dac.spring.entity.ProductEntity;
import com.dac.spring.model.enums.StatusName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPaginationRepository extends PagingAndSortingRepository<ProductEntity, Integer> {
    Page<ProductEntity> findAllByDeletedAndStatusNameAndCategoryIdAndQuantityGreaterThan(Pageable pageable, boolean deleted, StatusName statusName, int categoryID, int quantity);
    Page<ProductEntity> findAllByDeletedAndShopId(Pageable pageable, boolean deleted, int id);
    Page<ProductEntity> findAllByDeletedAndStatusNameAndQuantityGreaterThan(Pageable pageable, boolean deleted, StatusName statusName, int quantity);
    Page<ProductEntity> findAllByDeleted(Pageable pageable, boolean deleted);
}
