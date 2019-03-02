package com.dac.spring.repository;

import com.dac.spring.entity.CategoryEntity;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryPagingRepository extends PagingAndSortingRepository<CategoryEntity, Integer> {
}
