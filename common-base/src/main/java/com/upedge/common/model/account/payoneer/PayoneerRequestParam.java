package com.upedge.common.model.account.payoneer;

import lombok.Data;

@Data
public class PayoneerRequestParam {
    private String grant_type = "authorization_code";
    private String token;
    private PayoneerAuthParam body;
}
