package com.upedge.ums.modules.store.request;

import com.upedge.ums.modules.store.entity.Store;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author author
 */
@Data
public class StoreUpdateRequest{

    /**
     * 店铺名称
     */
    private String storeName;



    /**
     * 
     */
    private String currency;

    private BigDecimal customerUsdRate;
    /**
     * 店铺启用禁用
     */
    private Integer status;

    /**
     * 
     */
    private String timezone;


    public Store toStore(Long id){
        Store store=new Store();
        store.setId(id);
        store.setStoreName(storeName);
        store.setCurrency(currency);
        store.setStatus(status);
        store.setTimezone(timezone);
        store.setCustomerUsdRate(customerUsdRate);
        return store;
    }

}
