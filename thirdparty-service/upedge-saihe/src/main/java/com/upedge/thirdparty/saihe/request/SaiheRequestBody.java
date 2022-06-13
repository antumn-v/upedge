package com.upedge.thirdparty.saihe.request;

import com.upedge.thirdparty.saihe.entity.cancelOrderInfo.*;
import com.upedge.thirdparty.saihe.entity.createProcurement.ApiCreateProcurementRequest;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiGetOrderRequest;
import com.upedge.thirdparty.saihe.entity.getOrderSourceList.ApiGetOrderSourceRequest;
import com.upedge.thirdparty.saihe.entity.getProducts.ApiGetProductRequest;
import com.upedge.thirdparty.saihe.entity.getTransportList.ApiGetTransportRequest;
import com.upedge.thirdparty.saihe.entity.getWareHouseList.ApiGetWareHouseRequest;
import com.upedge.thirdparty.saihe.entity.processUpdateProduct.ApiUploadProductsRequest;
import com.upedge.thirdparty.saihe.entity.uploadOrder.ApiUploadOrderRequest;
import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cjq on 2018/12/4.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "soap12:Body")
@Data
public class SaiheRequestBody {

    /**
     * 赛盒订单渠道列表
     */
    @XmlElement(name="GetOrderSourceList")
    ApiGetOrderSourceRequest apiGetOrderSourceRequest;
    /**
     * 获取产品信息
     */
    @XmlElement(name="GetProducts")
    ApiGetProductRequest GetProducts;
    /**
     * 赛盒运输方式列表
     */
    @XmlElement(name="GetTransportList")
    ApiGetTransportRequest apiGetTransportRequest;
    /**
     * 赛盒仓库列表
     */
    @XmlElement(name="GetWareHouseList")
    ApiGetWareHouseRequest apiGetWareHouseRequest;
    /**
     * 上传产品
     */
    @XmlElement(name="ProcessUpdateProduct")
    ApiUploadProductsRequest apiUploadProductsRequest;

    /**
     * 创建备库单
     */
    @XmlElement(name="CreateProcurement")
    ApiCreateProcurementRequest apiCreateProcurementRequest;

    /**
     * 查询赛盒订单
     */
    @XmlElement(name="GetOrders")
    ApiGetOrderRequest apiGetOrderRequest;

    /**
     * 作废赛盒订单
     */
    @XmlElement(name="CancelOrderInfo")
    ApiCancelOrderRequest apiCancelOrderRequest;

    /**
     * 回传赛盒订单
     */
    @XmlElement(name="UpLoadOrderV2")
    ApiUploadOrderRequest apiUploadOrderRequest;

}
