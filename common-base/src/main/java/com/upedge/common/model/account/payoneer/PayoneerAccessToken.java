package com.upedge.common.model.account.payoneer;

import lombok.Data;

@Data
public class PayoneerAccessToken {


    private String token_type;
    private String access_token;
    private int expires_in;
    private int consented_on;
    private String scope;
    private String refresh_token;
    private int refresh_token_expires_in;
    private String id_token;
    private String error;
    private String error_description;

}
