package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.StoreProductVariant;
import lombok.Data;

@Data
public class StoreProductVariantUnSplitListRequest extends Page<StoreProductVariant> {

    Long customerId;
}
