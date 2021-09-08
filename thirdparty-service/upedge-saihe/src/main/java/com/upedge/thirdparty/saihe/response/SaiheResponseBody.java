package com.upedge.thirdparty.saihe.response;

import com.upedge.thirdparty.saihe.entity.cancelOrderInfo.ApiCancelOrderResponse;
import com.upedge.thirdparty.saihe.entity.createProcurement.ApiCreateProcurementResponse;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiGetOrderResponse;
import com.upedge.thirdparty.saihe.entity.getOrderSourceList.ApiGetOrderSourceResponse;
import com.upedge.thirdparty.saihe.entity.getProducts.ApiGetProductResponse;
import com.upedge.thirdparty.saihe.entity.getTransportList.ApiGetTransportResponse;
import com.upedge.thirdparty.saihe.entity.getWareHouseList.ApiGetWareHouseResponse;
import com.upedge.thirdparty.saihe.entity.processUpdateProduct.ApiUploadProductsResponse;
import com.upedge.thirdparty.saihe.entity.uploadOrder.UpLoadOrderV2Response;
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
public class SaiheResponseBody {


    /**
     * 赛盒订单渠道列表
     */
    @XmlElement(name="GetOrderSourceListResponse")
    ApiGetOrderSourceResponse apiGetOrderSourceResponse;

    /**
     * 获取产品信息
     */
    @XmlElement(name="GetProductsResponse")
    ApiGetProductResponse apiGetProductResponse;

    /**
     * 赛盒运输方式列表
     */
    @XmlElement(name="GetTransportListResponse")
    ApiGetTransportResponse apiGetTransportResponse;

    /**
     * 赛盒仓库列表
     */
    @XmlElement(name="GetWareHouseListResponse")
    ApiGetWareHouseResponse apiGetWareHouseResponse;

    /**
     * 上传产品
     */
    @XmlElement(name="ProcessUpdateProductResponse")
    ApiUploadProductsResponse apiUploadProductsResponse;

    /**
     * 创建备库单
     */
    @XmlElement(name="CreateProcurementResponse")
    ApiCreateProcurementResponse apiCreateProcurementResponse;

    /**
     * 查询赛盒订单
     */
    @XmlElement(name="GetOrdersResponse")
    ApiGetOrderResponse apiGetOrderResponse;

    /**
     * 作废赛盒订单
     */
    @XmlElement(name="CancelOrderInfoResponse")
    ApiCancelOrderResponse apiCancelOrderResponse;

    /**
     * 回传订单
     */
    @XmlElement(name="UpLoadOrderV2Response")
    UpLoadOrderV2Response upLoadOrderV2Response;

}
