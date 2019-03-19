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

    @Query(nativeQuery = true, value = "select * from campaign where start_date <= :startDate and end_date > :endDate and statusid= 1 and budget >= bid order by bid desc limit 0,3")
    List<CampaignEntity> getCampaign(String startDate, String endDate);

    @Query(nativeQuery = true, value = "select * from campaign where statusid= 1 and budget >= bid order by bid")
    List<CampaignEntity> getActiveCampaign();
}
