package com.dac.spring.repository;

import com.dac.spring.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {
    RatingEntity findByProductIdAndUserId(int pid, int uid);
    List<RatingEntity> findAllByProductId(int pid);

    @Query(value = "select avg(r.rate) from rating r where r.productid = ?1", nativeQuery = true)
    Float avg(int id);

    @Query(value = "select r.rate from rating r where r.productid = ?1 and r.userid = ?2", nativeQuery = true)
    Float myRating(int pid, int uid);
}