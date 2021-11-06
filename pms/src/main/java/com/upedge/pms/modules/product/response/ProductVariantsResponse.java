package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.pms.modules.product.entity.ProductVariant;

import java.util.List;

/**
 * @author author
 */
public class ProductVariantsResponse extends BaseResponse {

    public ProductVariantsResponse(int code, String msg, List<ProductVariant> data) {
        super(code, msg, data);
    }
    public ProductVariantsResponse(int code, String msg) {
        super(code, msg);
    }
}
