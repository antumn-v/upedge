package com.upedge.ums.modules.store.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.upedge.common.model.old.ums.AppStore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author author
 */
@Data
public class Store {

    /**
     *
     */
    private Long id;
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

    private String orgPath;
    /**
     *
     */

    private String apiToken;

    private String currency;

    private BigDecimal usdRate;

    @TableField(strategy = FieldStrategy.IGNORED)
    private BigDecimal customerUsdRate;
    /**
     * 店铺启用禁用
     */
    private Integer status = 1;
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

    /**
     * 是否启用自定义汇率
     */
    private Boolean rateCustomized;

    public Store(AppStore appStore) {
        this.id = appStore.getId();
        this.storeName = appStore.getStoreName();
        this.storeUrl = appStore.getStoreUrl();
        this.storeType = appStore.getSource();
        this.timezone = appStore.getTimezone();
        this.customerId = appStore.getUserId();
        this.currency = appStore.getCurrency();
        this.createTime = appStore.getCreateTime();
        this.updateTime = appStore.getUpdateTime();
        this.status = appStore.getStoreState();
        this.usdRate = appStore.getCurrencyRate();
        this.rateCustomized = appStore.getIsRateCustomized();
        this.customerUsdRate = appStore.getCurrencyRate();
    }

    public Store() {
    }
}
