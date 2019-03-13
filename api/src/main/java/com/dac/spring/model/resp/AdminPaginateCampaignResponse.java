package com.dac.spring.model.resp;

import java.util.List;

public class AdminPaginateCampaignResponse {
    private int totalPages;

    private List<AdminGetCampaignResponse> campaignResponseList;

    public AdminPaginateCampaignResponse(int totalPages, List<AdminGetCampaignResponse> campaignResponseList) {
        this.totalPages = totalPages;
        this.campaignResponseList = campaignResponseList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<AdminGetCampaignResponse> getCampaignResponseList() {
        return campaignResponseList;
    }

    public void setCampaignResponseList(List<AdminGetCampaignResponse> campaignResponseList) {
        this.campaignResponseList = campaignResponseList;
    }
}
