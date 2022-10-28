package com.upedge.thirdparty.shipcompany.yanwen.dto;

import com.upedge.thirdparty.shipcompany.yanwen.api.YanwenApi;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;

@Data
public class YanwenApiRequestBase {

    private String user_id = "20074603";

    private String format = "json";

    private String method;

    private long timestamp = System.currentTimeMillis();

    private String version = "V1.0";

    private String sign;

    private YanwenExpressDto data;

    public void initSign(String data,String method){
        this.sign = DigestUtils.md5Hex(YanwenApi.apiToken+YanwenApi.userId+ data +format+method+timestamp+version+YanwenApi.apiToken).toLowerCase();
    }
}
