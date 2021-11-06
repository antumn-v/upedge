package com.upedge.oms.modules.wholesale.request;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrder;
import lombok.Data;

/**
 * @author author
 */
@Data
public class WholesaleOrderListRequest extends Page<WholesaleOrder> {

    String tag;

    /**
     * 是否导入
     */
    private Integer bagFlag;
}
