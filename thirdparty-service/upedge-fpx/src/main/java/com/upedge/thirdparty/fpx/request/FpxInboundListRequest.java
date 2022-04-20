package com.upedge.thirdparty.fpx.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class FpxInboundListRequest {


    @JsonProperty("consignment_no")
    private String consignmentNo;
    @JsonProperty("ref_no")
    private String ref_no;
    @JsonProperty("4px_tracking_no")
    private String $4pxTrackingNo;
    @JsonProperty("warehouse_code")
    private String warehouseCode;
    @JsonProperty("logistics_product_code")
    private String logisticsProductCode;
    @JsonProperty("status")
    private String status;
    @JsonProperty("create_time_start")
    private long create_time_start;
    @JsonProperty("create_time_end")
    private long create_time_end;
    @JsonProperty("page_no")
    private Integer pageNo;
    @JsonProperty("page_size")
    private Integer pageSize;
}
