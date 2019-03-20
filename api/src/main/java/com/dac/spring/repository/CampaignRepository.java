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
    List<CampaignEntity> findByStartDateLessThanEqualAndEndDateGreaterThanAndStatusNameOrderByBidDesc(Date date1, Date date2, StatusName statusName);

    CampaignEntity findByNameContaining(String name);
    @Query(nativeQuery = true, value = "select * from campaign c where c.statusid= 1 and c.start_date <= ?1 and c.end_date > ?2 and c.budget >= c.bid order by c.bid desc limit 0,3")
    List<CampaignEntity> getCampaign(String startDate, String endDate);

    @Query(nativeQuery = true, value = "select * from campaign c where c.statusid= 1 and c.budget < c.bid order by c.bid desc ")
    List<CampaignEntity> getActiveCampaign();

    @Query(nativeQuery = true, value = "select * from campaign where end_date < ?1")
    List<CampaignEntity> getExpireCampaign(String endDate);

    @Query(nativeQuery = true, value = "select * from campaign where shopid= 3 limit ?1")
    List<CampaignEntity> getDefaultCampaign(int limit);
}
