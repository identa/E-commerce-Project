package com.dac.spring.utils.scheduler;

import com.dac.spring.entity.CampaignEntity;
import com.dac.spring.model.enums.StatusName;
import com.dac.spring.repository.CampaignRepository;
import com.dac.spring.repository.StatusRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CampaignJob implements Job {
    final static Logger logger = LoggerFactory.getLogger(CampaignJob.class);
    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    StatusRepository statusRepository;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<CampaignEntity> activeList = campaignRepository.findAllByStatusName(StatusName.ACTIVE);
        List<CampaignEntity> invalidList = new ArrayList<>();
        for (CampaignEntity campaign : activeList){
            if (campaign.getBudget() <= campaign.getSpend()){
                invalidList.add(campaign);
            }
        }
        List<CampaignEntity> expireList = campaignRepository.findAllByEndDateLessThan(new Date());

        for (CampaignEntity activeEntity : invalidList) {
            activeEntity.setStatus(statusRepository.findByName(StatusName.PAUSE));
            campaignRepository.save(activeEntity);
            logger.info("Update status campaign id " + activeEntity.getId());
        }

        for (CampaignEntity expireEntity :expireList){
            campaignRepository.delete(expireEntity);
            logger.info("Delete campaign id " + expireEntity.getId());
        }
    }
}
