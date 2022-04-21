package com.upedge.sms.modules.overseaWarehouse.vo;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import com.upedge.sms.modules.center.entity.ServiceOrderFreight;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OverseaWarehouseServiceOrderVo extends OverseaWarehouseServiceOrder {

    private String warehouseName;

    List<OverseaWarehouseServiceOrderItem> orderItems;

    List<ServiceOrderFreight> orderFreights;
}
