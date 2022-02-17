package com.upedge.oms.modules.stock.controller;


import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Applications;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.stock.dto.StockOrderListDto;
import com.upedge.oms.modules.stock.request.StockOrderListRequest;
import com.upedge.oms.modules.stock.request.StockOrderUpdateShipRequest;
import com.upedge.oms.modules.stock.response.StockOrderListResponse;
import com.upedge.oms.modules.stock.service.StockOrderService;
import com.upedge.oms.modules.stock.vo.StockOrderVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stockOrder/shipReview")
public class StockOrderShipReviewController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StockOrderService stockOrderService;

    @ApiOperation("备库订单运费审核列表")
    @PostMapping("/list")
    public BaseResponse list(@RequestBody @Valid StockOrderListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        if (session.getApplicationId() != Applications.UPEDGE_ADMING){
            return BaseResponse.failed();
        }
        if (null == request.getT()) {
            request.setT(new StockOrderListDto());
        }
        request.getT().setShipReview(0);
        request.initFromNum();


        List<StockOrderVo> results = stockOrderService.selectOrderList(request);
        Long total = stockOrderService.countOrderList(request);
        request.setTotal(total);
        StockOrderListResponse res = new StockOrderListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS, results, request);
        return res;
    }

    @ApiOperation("修改订单方式和运费")
    @PostMapping("/updateShip")
    public BaseResponse stockOrderUpdateShip(@RequestBody@Valid StockOrderUpdateShipRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return stockOrderService.updateShipDetail(session,request);
    }

    @ApiOperation("确认运费审核")
    @PostMapping("/{id}/confirm")
    public BaseResponse shipReviewConfirm(@PathVariable Long id){
        Session session = UserUtil.getSession(redisTemplate);
        return stockOrderService.confirmShipReview(id,session);
    }

}
