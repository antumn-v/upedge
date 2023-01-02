package com.upedge.ums.scheduler;

import com.upedge.ums.async.StoreAsync;
import com.upedge.ums.modules.store.entity.Store;
import com.upedge.ums.modules.store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StoreScheduler {

    @Autowired
    StoreService storeService;

    @Autowired
    StoreAsync storeAsync;

//    @Scheduled(cron = "0 00 */2 ? * *")
    public void getWoocommerceStoreOrder(){
        Store store = new Store();
        store.setStoreName("www.evershape.at");
        store = storeService.selectByPrimaryKey(store);
        storeAsync.getStoreData(store);
    }
}
