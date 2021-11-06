package com.upedge.oms.modules.order.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.oms.modules.order.service.OrderProfitService;
import com.upedge.oms.modules.order.vo.OrderProfitVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/order/profit")
public class OrderProfitController {
    @Autowired
    private OrderProfitService orderProfitService;

    @ApiOperation("订单利润")
    @GetMapping("/{orderId}")
    public BaseResponse orderProfit(@PathVariable Long orderId){
        OrderProfitVo orderProfit = orderProfitService.selectByPrimaryKey(orderId);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,orderProfit);
    }


}
