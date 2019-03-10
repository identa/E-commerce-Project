package com.dac.spring.repository;

import com.dac.spring.entity.OrderDetailEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderDetailPaginationRepository extends PagingAndSortingRepository<OrderDetailEntity, Integer> {
    Page<OrderDetailEntity> findAllByOrderId(Pageable pageable, int id);
}
