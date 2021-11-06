package com.upedge.common.model.store.response;

import com.upedge.common.base.BaseResponse;
import lombok.Data;

/**
 * @author 海桐
 */
@Data
public class StoreTokenResponse extends BaseResponse {

    private Long id;

    private String shop;

    private String token;

    private Long customerId;

    private Integer storeType;

    public StoreTokenResponse() {
    }

    public StoreTokenResponse(int code, Long id, String shop, String token, Long customerId, Integer storeType) {
        this.id = id;
        this.shop = shop;
        this.token = token;
        this.code = code;
        this.customerId = customerId;
        this.storeType = storeType;
    }

    public StoreTokenResponse(int code, String msg) {
        super(code, msg);
    }
}
