package com.upedge.pms.modules.product.request;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.product.vo.AppProductVo;
import lombok.Data;

/**
 * @author 海桐
 */
@Data
public class PrivateProductListRequest extends Page<AppProductVo> {

    private String orderBy = "create_time desc";

    private Long customerId;
}
