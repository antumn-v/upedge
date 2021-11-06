package com.upedge.oms.modules.wholesale.vo;

import com.upedge.oms.modules.wholesale.entity.WholesaleOrderAddress;
import lombok.Data;

import java.util.List;

@Data
public class WholesaleDetailsVo {


    private WholesaleOrderVo order;

    private List<WholesaleOrderItemVo> orderItems;

    private WholesaleOrderAddress orderAddress;

}
