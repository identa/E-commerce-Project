package com.dac.spring.repository;

import com.dac.spring.entity.ProductEntity;
import com.dac.spring.model.enums.StatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
    List<ProductEntity> findAllByOriginalPriceGreaterThanEqualAndOriginalPriceLessThanAndCategoryNameIn(double min, double max, List<String> name);

    @Query(nativeQuery = true, value = "select * from product p inner join category c on p.categoryid = c.id where p.current_price > ?1 and p.current_price <= ?2 and p.deleted = false and c.name in ?3 order by p.create_at desc")
    List<ProductEntity> getSortedProduct(double min, double max, List<String> names);

    @Query(nativeQuery = true, value = "select * from product p inner join category c on p.categoryid = c.id where p.current_price > ?1 and p.current_price <= ?2 and p.deleted = false and c.name in ?3 order by p.current_price desc")
    List<ProductEntity> getSortedProduct2(double min, double max, List<String> names);

    @Query(nativeQuery = true, value = "select * from product p inner join category c on p.categoryid = c.id where p.current_price > ?1 and p.current_price <= ?2 and p.deleted = false and c.name in ?3 order by p.current_price asc")
    List<ProductEntity> getSortedProduct3(double min, double max, List<String> names);

    @Query(nativeQuery = true, value = "select * from product p inner join category c on p.categoryid = c.id where p.current_price > ?1 and p.current_price <= ?2 and p.deleted = false and c.name in ?3 order by p.view desc")
    List<ProductEntity> getSortedProduct4(double min, double max, List<String> names);

    @Query(nativeQuery = true, value = "select * from product p inner join category c on p.categoryid = c.id where p.current_price > ?1 and p.current_price <= ?2 and p.deleted = false and c.name in ?3 order by p.ordered desc")
    List<ProductEntity> getSortedProduct5(double min, double max, List<String> names);
}
