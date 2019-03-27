package com.dac.spring.repository;

import com.dac.spring.entity.CampaignEntity;
import com.dac.spring.model.enums.StatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity, Integer> {
    List<CampaignEntity> findAllByStartDateLessThanEqualAndEndDateGreaterThanAndStatusNameOrderByBidDesc(Date date1, Date date2, StatusName statusName);
    CampaignEntity findByNameContaining(String name);
    List<CampaignEntity> findAllByStatusName(StatusName statusName);
    List<CampaignEntity> findAllByEndDateLessThan(Date date);
}
