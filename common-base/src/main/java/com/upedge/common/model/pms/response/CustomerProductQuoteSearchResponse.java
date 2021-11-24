package com.upedge.common.model.pms.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;

import java.util.List;

public class CustomerProductQuoteSearchResponse extends BaseResponse {

    protected List<CustomerProductQuoteVo> data;

    @Override
    public List<CustomerProductQuoteVo> getData() {
        return data;
    }

    public void setData(List<CustomerProductQuoteVo> data) {
        this.data = data;
    }

    public CustomerProductQuoteSearchResponse() {
    }

    public CustomerProductQuoteSearchResponse(int failCode, String messageFail) {
    }
    public CustomerProductQuoteSearchResponse(int failCode, String messageFail,List<CustomerProductQuoteVo> data) {
    }

    public static CustomerProductQuoteSearchResponse success(List<CustomerProductQuoteVo> data){
        return new CustomerProductQuoteSearchResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,data);
    }

    public static CustomerProductQuoteSearchResponse failed(){
        return new CustomerProductQuoteSearchResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
    }

    public static CustomerProductQuoteSearchResponse failed(String message){
        return new CustomerProductQuoteSearchResponse(ResultCode.FAIL_CODE, message);
    }
}
