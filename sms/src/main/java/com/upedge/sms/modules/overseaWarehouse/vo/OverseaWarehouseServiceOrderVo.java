package com.upedge.sms.modules.overseaWarehouse.vo;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderFreight;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OverseaWarehouseServiceOrderVo extends OverseaWarehouseServiceOrder {

    List<OverseaWarehouseServiceOrderItem> orderItems;

    List<OverseaWarehouseServiceOrderFreight> orderFreights;
}
