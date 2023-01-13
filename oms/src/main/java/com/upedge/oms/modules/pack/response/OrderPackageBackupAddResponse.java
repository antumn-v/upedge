package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.OrderPackageBackupAddRequest;
/**
 * @author gx
 */
public class OrderPackageBackupAddResponse extends BaseResponse {
    public OrderPackageBackupAddResponse(int code, String msg, Object data, OrderPackageBackupAddRequest req) {
        super(code,msg,data,req);
    }
}
