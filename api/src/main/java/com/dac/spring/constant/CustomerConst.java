package com.dac.spring.constant;

public class CustomerConst {
    private CustomerConst() {
    }

    public static final String CUSTOMER_NOT_FOUND = "Customer not found";
    public static final String PRODUCT_SUCCESS = "Products are returned successfully";
    public static final String AUTH = "Authorization";
    public static final int CAMPAIGN_AMOUNT = 3;
    public static final String CAMPAIGN_SQL = "select * from campaign where statusid= 1 and start_date <= ?1 and end_date > ?2 and budget >= bid order by bid desc limit 3";
}
