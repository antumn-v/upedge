package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.dto.StoreProductDto;
import lombok.Data;

/**
 * @author 海桐
 */
@Data
public class StoreProductListRequest extends Page<StoreProductDto> {

    private String orderBy = "create_at desc";

}
