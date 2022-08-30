package com.upedge.thirdparty.shipcompany.fpx.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class FpxOrderDto {

    @JSONField(name = "4px_inbound_date")
    private Long fpxInboundDate;
    @JSONField(name = "4px_outbound_date")
    private Long fpxOutboundDate;
    @JSONField(name = "4px_tracking_no")
    private String fpxTrackingNo;
    @JSONField(name = "consignment_create_date")
    private Long consignmentCreateDate;
    @JSONField(name = "consignment_status")
    private String consignmentStatus;
    @JSONField(name = "ds_consignment_no")
    private String dsConsignmentNo;
    @JSONField(name = "has_check_oda")
    private String hasCheckOda;
    @JSONField(name = "insure_status")
    private String insureStatus;
    @JSONField(name = "insure_type")
    private String insureType;
    @JSONField(name = "is_hold_sign")
    private String isHoldSign;
    @JSONField(name = "logistics_channel_no")
    private String logisticsChannelNo;
    @JSONField(name = "logistics_product_code")
    private String logisticsProductCode;
    @JSONField(name = "logistics_product_name")
    private String logisticsProductName;
    @JSONField(name = "oda_result_sign")
    private String odaResultSign;
    @JSONField(name = "ref_no")
    private String refNo;
}
