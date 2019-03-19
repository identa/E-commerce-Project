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

import java.util.List;

public class CampaignJob implements Job {
    final static Logger logger = LoggerFactory.getLogger(CampaignJob.class);
    @Autowired
    CampaignRepository campaignRepository;

    @Autowired
    StatusRepository statusRepository;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<CampaignEntity> list = campaignRepository.getActiveCampaign();

        for (CampaignEntity entity : list){
                entity.setStatus(statusRepository.findByName(StatusName.PAUSE));

                campaignRepository.save(entity);
                logger.info("Update status campaign id " + entity.getId());
            }
    }
}
