package com.upedge.oms.modules.order.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.request.MergeOrderRequest;
import com.upedge.oms.modules.order.request.MergeOrderShipListRequest;
import com.upedge.oms.modules.order.request.SplitNormalOrderRequest;
import com.upedge.oms.modules.order.service.OrderActionService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.vo.SameAddressOrderVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderActionController {

    @Autowired
    OrderActionService orderActionService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("普通订单拆分")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemId",value = "订单产品ID"),
            @ApiImplicitParam(name = "quantity",value = "订单产品数量")
    })
    @PostMapping("/{id}/split")
    public BaseResponse splitNormalOrder(@PathVariable Long id, @RequestBody @Valid SplitNormalOrderRequest request){
        String result = orderActionService.splitNormalOrder(id,request);
        if(result.equals("success")){
            return BaseResponse.success();
        }
        return BaseResponse.failed(result);
    }

    @ApiOperation("可合并订单列表")
    @PostMapping("/merge/list")
    public BaseResponse mergeList(){
        Session session = UserUtil.getSession(redisTemplate);

        List<SameAddressOrderVo> sameAddressOrderVoList = orderActionService.selectSameAddressOrderByStore(session.getCustomerId());


        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,sameAddressOrderVoList);
    }

    @ApiOperation("可合并订单运输列表")
    @PostMapping("/merge/ship/list")
    public BaseResponse mergeShipList(@RequestBody @Valid MergeOrderShipListRequest request){
        if (request.getOrderIds().size() == 1){
            return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,new ArrayList<>());
        }
        List<ShipDetail> shipDetails = orderActionService.mergeOrderShipList(request.getOrderIds());
        if(ListUtils.isEmpty(shipDetails)){
            shipDetails = new ArrayList<>();
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,shipDetails);
    }

    @ApiOperation("确认合并")
    @PostMapping("/merge/confirm")
    public BaseResponse mergeOrderConfirm(@RequestBody@Valid MergeOrderRequest request){
        String result = orderActionService.mergeNormalOrder(request);
        if(result.equals("success")){
            return BaseResponse.success();
        }
        return BaseResponse.failed(result);
    }

    @ApiOperation("还原订单")
    @PostMapping("/{id}/action/revert")
    public BaseResponse orderActionRevert(@PathVariable Long id){
        Order order = orderService.selectByPrimaryKey(id);
        if(order.getPayState() != 0 || order.getOrderType() == 1){
            return BaseResponse.failed();
        }
        String result = "request failed";
        switch (order.getOrderType()){
            case 0:
                return BaseResponse.success();
            case 2:
                result = orderActionService.restoreSplitOrder(order);
                break;
            case 3:
                result = orderActionService.revertMergedOrder(order);
                break;
            default:
                return BaseResponse.success();
        }
        if(result.equals("success")){
            return BaseResponse.success();
        }
        return BaseResponse.failed(result);
    }
}
