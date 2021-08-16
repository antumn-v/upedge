package com.upedge.ums.modules.store.request;

import com.upedge.ums.modules.store.entity.Store;
import java.util.Date;
import lombok.Data;
import java.math.BigDecimal;
/**
 * @author gx
 */
@Data
public class StoreUpdateRequest{

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
    private String orgPath;
    /**
     * 店铺货币
     */
    private String currency;
    /**
     * 店铺货币美元汇率
     */
    private BigDecimal usdRate;
    /**
     * 自定义美元汇率
     */
    private BigDecimal customerUsdRate;
    /**
     * 
     */
    private String apiToken;
    /**
     * 1 = 启用 0 = 禁用
     */
    private Integer status;
    /**
     * 0:shopify,1:woocommerce,2:shoplazza
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

    public Store toStore(Long id){
        Store store=new Store();
        store.setId(id);
        store.setStoreName(storeName);
        store.setStoreUrl(storeUrl);
        store.setUserId(userId);
        store.setCustomerId(customerId);
        store.setOrgId(orgId);
        store.setOrgPath(orgPath);
        store.setCurrency(currency);
        store.setUsdRate(usdRate);
        store.setCustomerUsdRate(customerUsdRate);
        store.setApiToken(apiToken);
        store.setStatus(status);
        store.setStoreType(storeType);
        store.setTimezone(timezone);
        store.setCreateTime(createTime);
        store.setUpdateTime(updateTime);
        return store;
    }

}
