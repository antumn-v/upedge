package com.upedge.tms.modules.warehouse.request;

import com.upedge.common.utils.IdGenerate;
import com.upedge.tms.modules.warehouse.entity.Warehouse;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class WarehouseAddRequest{

    /**
    * 
    */
    private String warehouseCode;
    /**
    * 0=本地仓 1=海外仓
    */
    private Integer warehouseType;
    /**
    * 
    */
    private String warehouseName;
    /**
    * 
    */
    private String warehouseEnEame;
    /**
    * 
    */
    private String country;
    /**
    * 
    */
    private String province;
    /**
    * 
    */
    private String city;
    /**
    * 
    */
    private String address1;
    /**
    * 
    */
    private String address2;
    /**
    * 
    */
    private String zip;
    /**
    * 
    */
    private String email;
    /**
    * 
    */
    private String phone;
    /**
    * 0=禁用 1=启用
    */
    private Integer state;

    public Warehouse toWarehouse(){
        Warehouse warehouse=new Warehouse();
        warehouse.setId(IdGenerate.nextId());
        warehouse.setWarehouseCode(warehouseCode);
        warehouse.setWarehouseType(warehouseType);
        warehouse.setWarehouseName(warehouseName);
        warehouse.setWarehouseEnEame(warehouseEnEame);
        warehouse.setCountry(country);
        warehouse.setProvince(province);
        warehouse.setCity(city);
        warehouse.setAddress1(address1);
        warehouse.setAddress2(address2);
        warehouse.setZip(zip);
        warehouse.setEmail(email);
        warehouse.setPhone(phone);
        warehouse.setState(state);
        return warehouse;
    }

}
