package com.upedge.ums.modules.store.request;

import com.upedge.ums.modules.store.entity.Store;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class StoreAddRequest{

                                    /**
             * 店铺名称
             */
            private String storeName;
                                /**
             * 店铺地址
             */
            private String storeUrl;
                                /**
             * 添加次店铺的用户
             */
            private Long userId;
                                /**
             * 
             */
            private Long customerId;
                                /**
             * 店铺对应的组织ID
             */
            private Long orgId;
                                /**
             * 
             */
            private String currency;
                                /**
             * 店铺启用禁用
             */
            private Integer status;
                                /**
             * 0:shopify,1:woocommerce
             */
            private Integer storeType;
                                /**
             * 
             */
            private String timezone;
                                /**
             * 店铺创建时间
             */
            private Date createTime;
                                /**
             * 
             */
            private Date updateTime;
            
    public Store toStore(){
        Store store=new Store();
        store.setStoreName(storeName);
        store.setStoreUrl(storeUrl);
        store.setUserId(userId);
        store.setCustomerId(customerId);
        store.setOrgId(orgId);
        store.setCurrency(currency);
        store.setStatus(status);
        store.setStoreType(storeType);
        store.setTimezone(timezone);
        store.setCreateTime(createTime);
        store.setUpdateTime(updateTime);
        return store;
    }

}
