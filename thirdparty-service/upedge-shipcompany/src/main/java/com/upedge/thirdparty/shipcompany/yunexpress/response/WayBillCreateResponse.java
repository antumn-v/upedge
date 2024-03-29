package com.upedge.thirdparty.shipcompany.yunexpress.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.upedge.thirdparty.shipcompany.yunexpress.vo.WayBillItemVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class WayBillCreateResponse {

    @JSONField(name = "Item")
    private List<WayBillItemVo> itemVos;


    @JSONField(name = "Code")
    private String code;
    @JSONField(name = "Message")
    private String message;
    @JSONField(name = "RequestId")
    private String requestId;
    @JSONField(name = "TimeStamp")
    private String timeStamp;
}
