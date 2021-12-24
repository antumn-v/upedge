package com.upedge.pms.modules.alibaba.request;

import com.upedge.pms.modules.alibaba.entity.AlibabaApi;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class AlibabaApiUpdateRequest{

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

    public AlibabaApi toAlibabaApi(String id){
        AlibabaApi alibabaApi=new AlibabaApi();
        alibabaApi.setApiSecret(apiSecret);
        alibabaApi.setAccessToken(accessToken);
        alibabaApi.setRefreshToken(refreshToken);
        alibabaApi.setUserId(userId);
        alibabaApi.setRefreshTokenCreateTime(refreshTokenCreateTime);
        alibabaApi.setRefreshTokenExpireTime(refreshTokenExpireTime);
        alibabaApi.setAccessTokenCreateTime(accessTokenCreateTime);
        alibabaApi.setAccessTokenExpireTime(accessTokenExpireTime);
        return alibabaApi;
    }

}
