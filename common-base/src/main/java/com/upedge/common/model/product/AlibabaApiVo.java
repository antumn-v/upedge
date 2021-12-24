package com.upedge.common.model.product;

import lombok.Data;

@Data
public class AlibabaApiVo {

    private String apiKey;
    /**
     *
     */
    private String apiSecret;
    /**
     *
     */
    private String accessToken;
    /**
     *
     */
    private String refreshToken;
    /**
     *
     */
    private String userId;
    /**
     *
     */
    private Long refreshTokenCreateTime;
    /**
     *
     */
    private Long refreshTokenExpireTime;
    /**
     *
     */
    private Long accessTokenCreateTime;
    /**
     *
     */
    private Long accessTokenExpireTime;
}
