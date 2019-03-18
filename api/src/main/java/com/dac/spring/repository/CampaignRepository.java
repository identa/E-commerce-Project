package com.dac.spring.repository;

import com.dac.spring.entity.CampaignEntity;
import com.dac.spring.model.enums.StatusName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity, Integer> {
    List<CampaignEntity> findTop3ByStartDateGreaterThanEqualAndEndDateLessThanAndStatusNameOrderByBidDesc(Date date1, Date date2, StatusName statusName);

    @Query("select 3 from CampaignEntity c where c.startDate <= ?1 and c.endDate > ?2 and c.status.name=?3 and c.budget >= c.bid order by c.bid desc ")
    List<CampaignEntity> getCampaign(Date startDate, Date endDate, StatusName statusName);
}
