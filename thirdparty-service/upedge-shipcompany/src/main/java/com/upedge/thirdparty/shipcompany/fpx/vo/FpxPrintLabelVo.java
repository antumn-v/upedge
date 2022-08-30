package com.upedge.thirdparty.shipcompany.fpx.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FpxPrintLabelVo {


    @JSONField(name = "data")
    private FpxPrintLabelDTO data;
    @JSONField(name = "msg")
    private String msg;
    @JSONField(name = "result")
    private String result;
    @JSONField(name = "errors")
    private List<FpxOrderErrorDTO> errors;

    @NoArgsConstructor
    @Data
    public static class FpxPrintLabelDTO {
        @JSONField(name = "label_barcode")
        private String labelBarcode;
        @JSONField(name = "label_url_info")
        private LabelUrlInfoDTO labelUrlInfo;

        @NoArgsConstructor
        @Data
        public static class LabelUrlInfoDTO {
            @JSONField(name = "logistics_label")
            private String logisticsLabel;
            @JSONField(name = "custom_label")
            private String customLabel;
            @JSONField(name = "package_label")
            private String packageLabel;
        }
    }
}
