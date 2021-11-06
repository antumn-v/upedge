package com.upedge.oms.modules.statistics.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.statistics.request.InvoiceExportRequestAddRequest;

/**
 * @author Ï¦Îí
 */
public class InvoiceExportRequestAddResponse extends BaseResponse {
    public InvoiceExportRequestAddResponse(int code, String msg, Object data, InvoiceExportRequestAddRequest req) {
        super(code,msg,data,req);
    }
}
