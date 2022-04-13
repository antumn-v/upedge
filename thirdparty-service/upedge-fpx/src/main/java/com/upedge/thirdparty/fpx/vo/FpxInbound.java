package com.upedge.thirdparty.fpx.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 入库委托
 */
@NoArgsConstructor
@Data
public class FpxInbound {


    @JsonProperty("customer_code")
    private String customerCode;
    @JsonProperty("consignment_no")
    private String consignmentNo;
    @JsonProperty("ref_no")
    private String refNo;
    @JsonProperty("to_warehouse_code")
    private String toWarehouseCode;
    @JsonProperty("status")
    private String status;
    @JsonProperty("total_volume")
    private String totalVolume;
    @JsonProperty("total_weight")
    private String totalWeight;
    @JsonProperty("4px_tracking_no")
    private String $4pxTrackingNo;
    @JsonProperty("transport_type")
    private String transportType;
    @JsonProperty("logistics_product_code")
    private String logisticsProductCode;
    @JsonProperty("signature_service")
    private String signatureService;
    @JsonProperty("shipping_no")
    private String shippingNo;
    @JsonProperty("insure_services")
    private String insureServices;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("insure_value")
    private String insureValue;
    @JsonProperty("remark")
    private String remark;
    @JsonProperty("ocustoms_type")
    private String ocustomsType;
    @JsonProperty("icustoms_type")
    private String icustomsType;
    @JsonProperty("create_time")
    private String createTime;
    @JsonProperty("create_user")
    private String createUser;
    @JsonProperty("lstsku")
    private List<FpxInboundSku> lstsku;

}
