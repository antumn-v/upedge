package com.upedge.tms.modules.warehouse.request;

import com.upedge.tms.modules.warehouse.entity.CountryAvailableWarehouse;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CountryAvailableWarehouseUpdateRequest{

    /**
     * 
     */
    private Long areaId;

    String warehouseCode;

    public CountryAvailableWarehouse toCountryAvailableWarehouse(String id){
        CountryAvailableWarehouse countryAvailableWarehouse=new CountryAvailableWarehouse();
        countryAvailableWarehouse.setWarehouseCode(warehouseCode);
        countryAvailableWarehouse.setAreaId(areaId);
        return countryAvailableWarehouse;
    }

}
