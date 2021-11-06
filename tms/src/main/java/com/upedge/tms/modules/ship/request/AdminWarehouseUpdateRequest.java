package com.upedge.tms.modules.ship.request;

import com.upedge.tms.modules.ship.entity.AdminWarehouse;
import lombok.Data;

/**
 * @author author
 */
@Data
public class AdminWarehouseUpdateRequest{

    /**
     * 
     */
    private String warehouseEname;


    public AdminWarehouse toAdminWarehouse(Integer id){
        AdminWarehouse adminWarehouse=new AdminWarehouse();
        adminWarehouse.setId(id);
        adminWarehouse.setWarehouseEname(warehouseEname);
        return adminWarehouse;
    }

}
