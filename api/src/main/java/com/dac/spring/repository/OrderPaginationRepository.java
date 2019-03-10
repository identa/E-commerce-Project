package com.dac.spring.repository;

import com.dac.spring.entity.OrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrderPaginationRepository extends PagingAndSortingRepository<OrderEntity, Integer> {
}
