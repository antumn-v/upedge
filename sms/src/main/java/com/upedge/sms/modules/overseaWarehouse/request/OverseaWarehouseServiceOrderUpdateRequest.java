package com.upedge.sms.modules.overseaWarehouse.request;

import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class OverseaWarehouseServiceOrderUpdateRequest{

    private Long id;

    private String remark;

    public OverseaWarehouseServiceOrder toOverseaWarehouseServiceOrder(){
        OverseaWarehouseServiceOrder overseaWarehouseServiceOrder=new OverseaWarehouseServiceOrder();
        overseaWarehouseServiceOrder.setId(id);
        overseaWarehouseServiceOrder.setRemark(remark);
        return overseaWarehouseServiceOrder;
    }

}
