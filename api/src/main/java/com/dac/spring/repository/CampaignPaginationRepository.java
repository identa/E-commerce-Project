package com.dac.spring.repository;

import com.dac.spring.entity.CampaignEntity;
import com.dac.spring.model.enums.StatusName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignPaginationRepository extends PagingAndSortingRepository<CampaignEntity, Integer> {
    Page<CampaignEntity> findAllByStatusName(Pageable pageable,StatusName statusName);
}
