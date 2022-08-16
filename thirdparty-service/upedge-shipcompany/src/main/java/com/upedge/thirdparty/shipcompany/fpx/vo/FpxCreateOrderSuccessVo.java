package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FpxCreateOrderSuccessVo {


    @JsonProperty("data")
    private FpxCreateOrderDataDTO data;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("result")
    private String result;
    @JsonProperty("errors")
    private List<FpxOrderErrorDTO> errors;

    @NoArgsConstructor
    @Data
    public static class FpxCreateOrderDataDTO {
        @JsonProperty("ds_consignment_no")
        private String dsConsignmentNo;
        @JsonProperty("4px_tracking_no")
        private String fpxTrackingNo;
        @JsonProperty("label_barcode")
        private String labelBarcode;
        @JsonProperty("logistics_channel_name")
        private String logisticsChannelName;
        @JsonProperty("ref_no")
        private String refNo;
        @JsonProperty("logistics_channel_no")
        private String logisticsChannelNo;
    }
}
