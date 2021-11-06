package com.upedge.oms.modules.pack.request;

import com.upedge.oms.modules.pack.entity.PackageOrder;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class PackageOrderAddRequest{

    /**
    * 
    */
    private Date shipDate;

    public PackageOrder toPackageOrder(){
        PackageOrder packageOrder=new PackageOrder();
        packageOrder.setShipDate(shipDate);
        return packageOrder;
    }

}
