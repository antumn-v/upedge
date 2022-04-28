package com.upedge.pms.modules.alibaba.entity.product;

import com.alibaba.ocean.rawsdk.client.APIId;
import com.alibaba.ocean.rawsdk.common.AbstractAPIRequest;

/**
 * Created by guoxing on 2020/6/15.
 */
public class AlibabaCrossProductInfoParam extends AbstractAPIRequest<AlibabaCrossProductInfoResult> {

    //https://open.1688.com/api/apidocdetail.htm?id=com.alibaba.product:alibaba.cross.productInfo-1
    public AlibabaCrossProductInfoParam() {
        super();
        oceanApiId = new APIId("com.alibaba.product", "alibaba.cross.productInfo", 1);
    }

    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
