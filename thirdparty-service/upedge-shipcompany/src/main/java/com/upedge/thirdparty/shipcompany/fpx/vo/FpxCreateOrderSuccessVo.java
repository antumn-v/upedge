package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FpxCreateOrderSuccessVo {


    @JSONField(name = "data")
    private FpxCreateOrderDataDTO data;
    @JSONField(name = "msg")
    private String msg;
    @JSONField(name = "result")
    private String result;
    @JSONField(name = "errors")
    private List<FpxOrderErrorDTO> errors;

    @NoArgsConstructor
    @Data
    public static class FpxCreateOrderDataDTO {
        @JSONField(name = "ds_consignment_no")
        private String dsConsignmentNo;
        @JSONField(name = "4px_tracking_no")
        private String fpxTrackingNo;
        @JSONField(name = "label_barcode")
        private String labelBarcode;
        @JSONField(name = "logistics_channel_name")
        private String logisticsChannelName;
        @JSONField(name = "ref_no")
        private String refNo;
        @JSONField(name = "logistics_channel_no")
        private String logisticsChannelNo;
    }
}
