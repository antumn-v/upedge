package com.upedge.ums.modules.account.entity.payoneer;

import lombok.Data;

@Data
public class PayoneerToken {


    private String token_type;
    private String access_token;
    private int expires_in;
    private Integer consented_on;
    private String scope;
    private String refresh_token;
    private int refresh_token_expires_in;
    private String id_token;
    private String account_id;
    private String error;
    private String error_description;

}
