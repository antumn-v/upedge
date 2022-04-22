package com.upedge.sms.modules.wholesale;

import com.upedge.sms.modules.center.entity.ServiceOrderFreight;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrderAddress;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrderItem;
import lombok.Data;

import java.util.List;

@Data
public class WholesaleOrderVo extends WholesaleOrder {


    List<WholesaleOrderItem> wholesaleOrderItems;

    List<ServiceOrderFreight> orderFreights;

    WholesaleOrderAddress address;

}
