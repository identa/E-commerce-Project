package com.dac.spring.repository;

import com.dac.spring.constant.CustomerConst;
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

    @Query(nativeQuery = true, value = CustomerConst.CAMPAIGN_SQL)
    List<CampaignEntity> getCampaign(String startDate, String endDate);

    @Query(nativeQuery = true, value = "select * from campaign where statusid= 1 and budget < bid order by bid desc ")
    List<CampaignEntity> getActiveCampaign();

    @Query(nativeQuery = true, value = "select * from campaign where end_date < ?1")
    List<CampaignEntity> getExpireCampaign(String endDate);

    @Query(nativeQuery = true, value = "select * from campaign where shopid= 3 limit ?1")
    List<CampaignEntity> getDefaultCampaign(int limit);
}
