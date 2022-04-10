package com.upedge.oms.modules.common.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.oms.modules.common.entity.SaiheOrderRecord;
import com.upedge.oms.modules.common.request.SaiheOrderRecordListRequest;
import com.upedge.oms.modules.common.response.SaiheOrderRecordListResponse;
import com.upedge.oms.modules.common.service.SaiheOrderRecordService;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderRefund;
import com.upedge.oms.modules.order.service.OrderRefundService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.thirdparty.saihe.entity.cancelOrderInfo.ApiCancelOrderResponse;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiGetOrderResponse;
import com.upedge.thirdparty.saihe.entity.getOrderByCode.ApiOrderInfo;
import com.upedge.thirdparty.saihe.service.SaiheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api("订单上传赛盒记录")
@RestController
@RequestMapping("/saiheOrderRecord")
public class SaiheOrderRecordController {
    @Autowired
    private SaiheOrderRecordService saiheOrderRecordService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRefundService orderRefundService;


    @ApiOperation("列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "common:saiheorderrecord:list")
    public SaiheOrderRecordListResponse list(@RequestBody @Valid SaiheOrderRecordListRequest request) {
        List<SaiheOrderRecord> results = saiheOrderRecordService.select(request);
        Long total = saiheOrderRecordService.count(request);
        request.setTotal(total);
        SaiheOrderRecordListResponse res = new SaiheOrderRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }
    
    @PostMapping("/test")
    public BaseResponse test(){
        List<String> clientIds = new ArrayList<>();
        List<SaiheOrderRecord> saiheOrderRecords = saiheOrderRecordService.selectTwiceUploadOrder();
        for (SaiheOrderRecord saiheOrderRecord : saiheOrderRecords) {
            Order order = orderService.selectByPrimaryKey(Long.parseLong(saiheOrderRecord.getClientOrderCode()));
            if (order.getSaiheOrderCode() != null
            && !order.getSaiheOrderCode().equals(saiheOrderRecord.getSaiheOrderCode())){

                ApiGetOrderResponse apiGetOrderResponse = SaiheService.getOrderByCode(saiheOrderRecord.getSaiheOrderCode());
                if (apiGetOrderResponse.getGetOrdersResult().getStatus().equals("OK")) {
                    List<ApiOrderInfo> l = apiGetOrderResponse.getGetOrdersResult().getOrderInfoList().getOrderInfoList();
                    if (l != null && l.size() > 0) {
                        if (l != null && l.size() > 0) {
                            Integer orderState = l.get(0).getOrderState();
                            Integer orderStatus = l.get(0).getOrderStatus();
                            //赛盒未发货
                            if (orderState == 0) {
                                //订单已作废
                                if (orderStatus == 2) {
                                    saiheOrderRecordService.deleteByPrimaryKey(saiheOrderRecord.getId());
                                } else {
                                    //作废订单
                                    //作废成功
                                    ApiCancelOrderResponse apiCancelOrderResponse = SaiheService.cancelOrderInfo(saiheOrderRecord.getSaiheOrderCode());
                                    if (apiCancelOrderResponse.getCancelOrderResult().getStatus().equals("OK") &&
                                            apiCancelOrderResponse.getCancelOrderResult().getSuccess()) {
                                        saiheOrderRecordService.deleteByPrimaryKey(saiheOrderRecord.getId());
                                        //message.append("赛盒未发货,订单作废成功!</br> ");
                                    } else {
                                        clientIds.add(saiheOrderRecord.getSaiheOrderCode());
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return BaseResponse.success(clientIds);
    }



}
