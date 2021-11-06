package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.entity.AdminWarehouse;
import lombok.Data;

/**
 * @author author
 */
@Data
public class AdminWarehouseAddRequest{

    /**
    * 
    */
    private String warehouseName;
    /**
    * 仓库类型
    */
    private Integer warehouseType;
    /**
    * 
    */
    private String warehouseEname;
    /**
    * 状态 1:可用 0:禁用
    */
    private Integer state;

    public AdminWarehouse toAdminWarehouse(){
        AdminWarehouse adminWarehouse=new AdminWarehouse();
        adminWarehouse.setWarehouseName(warehouseName);
        adminWarehouse.setWarehouseType(warehouseType);
        adminWarehouse.setWarehouseEname(warehouseEname);
        adminWarehouse.setState(state);
        return adminWarehouse;
    }

}
