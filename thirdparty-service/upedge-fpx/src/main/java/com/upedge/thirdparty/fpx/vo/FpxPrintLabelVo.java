package com.upedge.thirdparty.fpx.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class FpxPrintLabelVo {


    @JsonProperty("data")
    private FpxPrintLabelDTO data;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("result")
    private String result;
    @JsonProperty("errors")
    private List<FpxOrderErrorDTO> errors;

    @NoArgsConstructor
    @Data
    public static class FpxPrintLabelDTO {
        @JsonProperty("label_barcode")
        private String labelBarcode;
        @JsonProperty("label_url_info")
        private LabelUrlInfoDTO labelUrlInfo;

        @NoArgsConstructor
        @Data
        public static class LabelUrlInfoDTO {
            @JsonProperty("logistics_label")
            private String logisticsLabel;
            @JsonProperty("custom_label")
            private String customLabel;
            @JsonProperty("package_label")
            private String packageLabel;
        }
    }
}
