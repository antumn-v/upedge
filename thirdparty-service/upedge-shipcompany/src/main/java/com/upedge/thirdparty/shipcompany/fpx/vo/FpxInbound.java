package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 入库委托
 */
@NoArgsConstructor
@Data
public class FpxInbound {


    @JSONField(name = "customer_code")
    private String customerCode;
    @JSONField(name = "consignment_no")
    private String consignmentNo;
    @JSONField(name = "ref_no")
    private String refNo;
    @JSONField(name = "to_warehouse_code")
    private String toWarehouseCode;
    @JSONField(name = "status")
    private String status;
    @JSONField(name = "total_volume")
    private String totalVolume;
    @JSONField(name = "total_weight")
    private String totalWeight;
    @JSONField(name = "4px_tracking_no")
    private String $4pxTrackingNo;
    @JSONField(name = "transport_type")
    private String transportType;
    @JSONField(name = "logistics_product_code")
    private String logisticsProductCode;
    @JSONField(name = "signature_service")
    private String signatureService;
    @JSONField(name = "shipping_no")
    private String shippingNo;
    @JSONField(name = "insure_services")
    private String insureServices;
    @JSONField(name = "currency")
    private String currency;
    @JSONField(name = "insure_value")
    private String insureValue;
    @JSONField(name = "remark")
    private String remark;
    @JSONField(name = "ocustoms_type")
    private String ocustomsType;
    @JSONField(name = "icustoms_type")
    private String icustomsType;
    @JSONField(name = "create_time")
    private String createTime;
    @JSONField(name = "create_user")
    private String createUser;
    @JSONField(name = "lstsku")
    private List<FpxInboundSku> lstsku;

}
