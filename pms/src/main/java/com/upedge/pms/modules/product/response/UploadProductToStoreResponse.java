package com.upedge.pms.modules.product.response;

import com.upedge.common.base.BaseResponse;

/**
 * @author 海桐
 */
public class UploadProductToStoreResponse extends BaseResponse {

    public UploadProductToStoreResponse(int code, String msg) {
        super(code, msg);
    }

    public UploadProductToStoreResponse(int code, String msg, Object data) {
        super(code, msg, data);
    }
}
