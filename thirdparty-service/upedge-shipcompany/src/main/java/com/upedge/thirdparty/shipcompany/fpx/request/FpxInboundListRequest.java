package com.upedge.thirdparty.shipcompany.fpx.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FpxInboundListRequest {


    @JSONField(name = "consignment_no")
    private String consignmentNo;
    @JSONField(name = "ref_no")
    private String ref_no;
    @JSONField(name = "4px_tracking_no")
    private String $4pxTrackingNo;
    @JSONField(name = "warehouse_code")
    private String warehouseCode;
    @JSONField(name = "logistics_product_code")
    private String logisticsProductCode;
    @JSONField(name = "status")
    private String status;
    @JSONField(name = "create_time_start")
    private long create_time_start;
    @JSONField(name = "create_time_end")
    private long create_time_end;
    @JSONField(name = "page_no")
    private Integer pageNo;
    @JSONField(name = "page_size")
    private Integer pageSize;
}
