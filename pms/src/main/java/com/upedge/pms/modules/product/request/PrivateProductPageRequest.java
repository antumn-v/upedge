package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询产品管理--> 个人产品池
 * @author author
 */
public class PrivateProductPageRequest extends Page<Product> {

    private String managerCode;

    private String variantSku;

    public String getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
    }

    private List<Long> productIds=new ArrayList<>();

    public String getVariantSku() {
        return variantSku;
    }

    public void setVariantSku(String variantSku) {
        this.variantSku = variantSku;
    }


    public List<Long> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Long> productIds) {
        this.productIds = productIds;
    }
}
