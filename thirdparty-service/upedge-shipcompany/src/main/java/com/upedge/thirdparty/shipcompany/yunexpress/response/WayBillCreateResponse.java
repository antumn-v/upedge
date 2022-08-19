package com.upedge.thirdparty.shipcompany.yunexpress.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.upedge.thirdparty.shipcompany.yunexpress.vo.WayBillItemVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WayBillCreateResponse {

    @JsonProperty("Item")
    private List<WayBillItemVo> itemVos;


    @JsonProperty("Code")
    private String code;
    @JsonProperty("Message")
    private String message;
    @JsonProperty("RequestId")
    private String requestId;
    @JsonProperty("TimeStamp")
    private String timeStamp;
}
