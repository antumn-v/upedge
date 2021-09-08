package com.upedge.thirdparty.saihe.response;

import com.upedge.thirdparty.saihe.entity.GetProductInventory.ApiGetProductInventoryRequest;
import com.upedge.thirdparty.saihe.entity.getPackages.ApiGetPackagesRequest;
import com.upedge.thirdparty.saihe.entity.getPackages.ApiGetPackagesResponse;
import com.upedge.thirdparty.saihe.entity.getProcurementList.GetProcurementListRequest;
import com.upedge.thirdparty.saihe.entity.getProcurementList.GetProcurementListResponse;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cjq on 2018/12/4.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "soap:Body")
@Data
public class RequestBody1 {

    /**
     * 获取赛盒包裹
     */
    @XmlElement(name="GetPackages")
    ApiGetPackagesRequest apiGetPackagesRequest;
    /**
     * 获取赛盒包裹
     */
    @XmlElement(name ="GetPackagesResponse")
    ApiGetPackagesResponse apiGetPackagesResponse;


    @XmlElement(name="GetProcurementList")
    GetProcurementListRequest getProcurementListRequest;
    @XmlElement(name="GetProcurementListResponse")
    GetProcurementListResponse getProcurementListResponse;


    //查询库存
    @XmlElement(name="GetProductInventory")
    ApiGetProductInventoryRequest apiGetProductInventoryRequest;
    @XmlElement(name="GetProductInventoryResponse")
    GetProductInventoryResponse getProductInventoryResponse;
}
