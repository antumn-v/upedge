package com.upedge.oms.modules.order.controller;


import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.ResultCode;
import com.upedge.thirdparty.saihe.config.SaiheConfig;
import com.upedge.thirdparty.saihe.entity.getOrderSourceList.ApiGetOrderSourceResponse;
import com.upedge.thirdparty.saihe.entity.getOrderSourceList.ApiOrderSource;
import com.upedge.thirdparty.saihe.modules.source.SaiheSourceApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orderSource")
public class OrderSourceController {

    @RequestMapping(value="/admin/list", method= RequestMethod.POST)
    public BaseResponse orderSourceList() {
        List<ApiOrderSource> orderSourceList=new ArrayList<>();
        ApiGetOrderSourceResponse apiGetOrderSourceResponse=
                SaiheSourceApi.getOrderSourceList(SaiheConfig.SOURCINBOX_ORDER_SOURCE_TYPE);
        if(apiGetOrderSourceResponse!=null&&
                apiGetOrderSourceResponse.getGetOrderSourceListResult()!=null&&
                apiGetOrderSourceResponse.getGetOrderSourceListResult().getOrderSourceList()!=null){
            orderSourceList=apiGetOrderSourceResponse.getGetOrderSourceListResult()
                    .getOrderSourceList().getApiOrderSource();
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE,orderSourceList);
    }
}
