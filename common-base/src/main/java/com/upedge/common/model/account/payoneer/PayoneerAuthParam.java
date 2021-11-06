package com.upedge.common.model.account.payoneer;

import lombok.Data;

@Data
public class PayoneerAuthParam {
    private String grant_type;
    private String scope = "read write openid personal-details";
    private String redirect_uri;
    private String code;
    private String token;
    private String client_id;
    private String client_secret;
    private String accountId;
}
