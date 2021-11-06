package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.entity.StoreProductAttribute;
import lombok.Data;

import java.util.Set;

/**
 * @author author
 */
@Data
public class StoreProductAttributeListRequest extends Page<StoreProductAttribute> {

    private Set custormerIds;

    private Long adminUserId;

    private Boolean orderBySale;

}
