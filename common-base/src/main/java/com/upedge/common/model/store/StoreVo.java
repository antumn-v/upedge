package com.upedge.common.model.store;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

@Data
public class StoreVo {

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

    private String trackingUrl;

    private boolean emailPrompt=false;

    public String getTrackingUrl(String trackingCode) {
        if(StringUtils.isBlank(trackingUrl)){
            return "https://t.17track.net/en#nums="+trackingCode;
        }
        return trackingUrl+trackingCode;
    }
}
