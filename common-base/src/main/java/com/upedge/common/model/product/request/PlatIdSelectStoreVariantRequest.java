package com.upedge.common.model.product.request;

import com.upedge.common.model.product.StoreProductVariantVo;
import lombok.Data;

import java.util.List;

@Data
public class PlatIdSelectStoreVariantRequest {

    Long storeId;

    List<StoreProductVariantVo> variantVos;

    List<Long> storeVariantIds;

    Long storeVariantId;
}
