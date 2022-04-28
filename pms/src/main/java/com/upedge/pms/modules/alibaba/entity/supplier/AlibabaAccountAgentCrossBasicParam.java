package com.upedge.pms.modules.alibaba.entity.supplier;

import com.alibaba.ocean.rawsdk.client.APIId;
import com.alibaba.ocean.rawsdk.common.AbstractAPIRequest;

/**
 * Created by guoxing on 2020/6/9.
 */
public class AlibabaAccountAgentCrossBasicParam extends AbstractAPIRequest<AlibabaAccountAgentCrossBasicResult> {

    //https://open.1688.com/api/apidocdetail.htm?id=com.alibaba.account:alibaba.account.agent.crossBasic-1&aopApiCategory=member
    public AlibabaAccountAgentCrossBasicParam() {
        super();
        oceanApiId = new APIId("com.alibaba.account", "alibaba.account.agent.crossBasic", 1);
    }

    String loginId;

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
}
