package com.upedge.pms.scheduler;

import com.upedge.common.feign.OmsFeignClient;
import org.springframework.beans.factory.annotation.Autowired;

public class StoreProductSalesScheduler {

    @Autowired
    OmsFeignClient omsFeignClient;

}
