package com.upedge.common.model.pms.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import lombok.Data;

import java.util.List;
@Data
public class QuotedProductSelectBySkuResponse  extends BaseResponse {

    List<CustomerProductQuoteVo> customerProductQuoteVos;


    public QuotedProductSelectBySkuResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public QuotedProductSelectBySkuResponse(int code, List<CustomerProductQuoteVo> data) {
        this.code = code;
        this.data = data;
    }

    public QuotedProductSelectBySkuResponse(int code, String msg, List<CustomerProductQuoteVo> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public QuotedProductSelectBySkuResponse(int code, String msg, List<CustomerProductQuoteVo> data, Object req) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.req = req;
    }

    public QuotedProductSelectBySkuResponse(List<CustomerProductQuoteVo> customerProductQuoteVos) {
        this.customerProductQuoteVos = customerProductQuoteVos;
    }

    public QuotedProductSelectBySkuResponse() {
    }

    public QuotedProductSelectBySkuResponse(int code, Object data, List<CustomerProductQuoteVo> customerProductQuoteVos) {
        super(code, data);
        this.customerProductQuoteVos = customerProductQuoteVos;
    }
}
