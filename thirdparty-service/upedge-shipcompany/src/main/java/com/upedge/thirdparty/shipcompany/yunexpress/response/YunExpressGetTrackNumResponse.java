package com.upedge.thirdparty.shipcompany.yunexpress.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.upedge.thirdparty.shipcompany.yunexpress.dto.YunExpressGetTrackNumDto;
import lombok.Data;

import java.util.List;

@Data
public class YunExpressGetTrackNumResponse {

    @JSONField(name = "Items")
    private List<YunExpressGetTrackNumDto> yunExpressGetTrackNumDtos;

    @JSONField(name = "Code")
    private String code;
    @JSONField(name = "Message")
    private String message;
    @JSONField(name = "RequestId")
    private String requestId;
    @JSONField(name = "TimeStamp")
    private String timeStamp;
}
