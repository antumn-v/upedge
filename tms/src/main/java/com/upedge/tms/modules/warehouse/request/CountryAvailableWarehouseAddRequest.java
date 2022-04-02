package com.upedge.tms.modules.warehouse.request;

import com.upedge.tms.modules.warehouse.entity.CountryAvailableWarehouse;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class CountryAvailableWarehouseAddRequest{

    /**
    * 
    */
    private Long areaId;

    public CountryAvailableWarehouse toCountryAvailableWarehouse(){
        CountryAvailableWarehouse countryAvailableWarehouse=new CountryAvailableWarehouse();
        countryAvailableWarehouse.setAreaId(areaId);
        return countryAvailableWarehouse;
    }

}
