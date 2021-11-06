package com.upedge.oms.modules.statistics.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.statistics.request.InvoiceExportRequestListRequest;

/**
 * @author Ï¦Îí
 */
public class InvoiceExportRequestListResponse extends BaseResponse {
    public InvoiceExportRequestListResponse(int code, String msg, Object data, InvoiceExportRequestListRequest req) {
        super(code,msg,data,req);
    }
}
