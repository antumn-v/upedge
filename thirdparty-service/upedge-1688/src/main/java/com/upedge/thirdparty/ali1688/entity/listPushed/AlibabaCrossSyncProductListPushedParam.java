package com.upedge.thirdparty.ali1688.entity.listPushed;

import com.alibaba.ocean.rawsdk.client.APIId;
import com.alibaba.ocean.rawsdk.common.AbstractAPIRequest;

/**
 * Created by jiaqi on 2020/6/15.
 */
public class AlibabaCrossSyncProductListPushedParam  extends AbstractAPIRequest<AlibabaCrossSyncProductListPushedResult> {

    //https://open.1688.com/api/apidocdetail.htm?id=com.alibaba.product.push:alibaba.cross.syncProductListPushed-1
    public AlibabaCrossSyncProductListPushedParam() {
        super();
        oceanApiId = new APIId("com.alibaba.product.push", "alibaba.cross.syncProductListPushed", 1);
    }

    /**
     * 1688的商品ID列表,列表长度不能超过20个
     */
    private Long[] productIdList;

    public Long[] getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(Long[] productIdList) {
        this.productIdList = productIdList;
    }

}
