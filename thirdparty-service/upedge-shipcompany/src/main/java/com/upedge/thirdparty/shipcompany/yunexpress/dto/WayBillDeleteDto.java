package com.upedge.thirdparty.shipcompany.yunexpress.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class WayBillDeleteDto {


    @JSONField(name = "Item")
    private WayBillDeleteItemDTO item;
    @JSONField(name = "Code")
    private String code;
    @JSONField(name = "Message")
    private String message;
    @JSONField(name = "RequestId")
    private String requestId;
    @JSONField(name = "TimeStamp")
    private String timeStamp;

    @NoArgsConstructor
    @Data
    public static class WayBillDeleteItemDTO {
        @JSONField(name = "OrderNumber")
        private String orderNumber;
        @JSONField(name = "Status")
        private String status;
        @JSONField(name = "Type")
        private Integer type;
        @JSONField(name = "Remark")
        private String remark;
    }
}
