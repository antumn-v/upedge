package com.upedge.thirdparty.shipcompany.yunexpress.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class YunExpressLabelVo {

    @JSONField(name = "Url")
    private String url;


    @JSONField(name = "OrderInfos")
    private List<OrderInfosDTO> orderInfos;

    @NoArgsConstructor
    @Data
    public static class OrderInfosDTO {
        @JSONField(name = "CustomerOrderNumber")
        private String customerOrderNumber;
        @JSONField(name = "Error")
        private String error;
        @JSONField(name = "Code")
        private Integer code;
    }
}
