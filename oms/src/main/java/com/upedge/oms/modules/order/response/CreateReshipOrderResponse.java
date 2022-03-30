package com.upedge.oms.modules.order.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.order.entity.Order;
import lombok.Data;

@Data
public class CreateReshipOrderResponse extends BaseResponse {


    public CreateReshipOrderResponse(int successCode, String messageSuccess, Order data, Object req) {
    }

    public CreateReshipOrderResponse(int failCode, String message) {
    }

    public CreateReshipOrderResponse() {
    }

    public static CreateReshipOrderResponse failed(){
        return new CreateReshipOrderResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
    }

    public static CreateReshipOrderResponse failed(String message){
        return new CreateReshipOrderResponse(ResultCode.FAIL_CODE, message);
    }

    public static CreateReshipOrderResponse success(Order data , Object req){
        return new CreateReshipOrderResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,data,req);
    }

}
