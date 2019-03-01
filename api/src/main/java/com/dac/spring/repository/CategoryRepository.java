package com.dac.spring.repository;

import com.dac.spring.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    List<CategoryEntity> findAllByParentID(int parentID);
    Optional<CategoryEntity> findByName(String name);
    boolean existsByName(String name);
}
