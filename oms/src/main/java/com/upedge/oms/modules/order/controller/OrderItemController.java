package com.upedge.oms.modules.order.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.enums.CustomerExceptionEnum;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.pms.request.OrderQuoteApplyRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.request.AirwallexRequest;
import com.upedge.oms.modules.order.request.OrderItemQuoteRequest;
import com.upedge.oms.modules.order.request.OrderItemUpdateQuantityRequest;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author author
 */
@Api(tags = "订单产品管理")
@RestController
@RequestMapping("/orderItem")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    @Autowired
    PmsFeignClient pmsFeignClient;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("修改订单数量")
    @PostMapping("/{id}/updateQuantity")
    public BaseResponse orderItemUpdateQuantity(@PathVariable Long id, @RequestBody @Valid OrderItemUpdateQuantityRequest request){
        int i = orderItemService.updateQuantity(request,id);
        if(i == 1){
            return BaseResponse.success();
        }
        return BaseResponse.failed();
    }

    @ApiOperation("提交报价申请")
    @PostMapping("/quoteApply")
    public BaseResponse itemQuoteApply(@RequestBody OrderItemQuoteRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        Order order = orderService.selectByPrimaryKey(request.getOrderId());
        Page<OrderItem> orderItemPage = new Page<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(request.getOrderId());
        orderItemPage.setPageSize(-1);
        List<OrderItem> orderItems = orderItemService.select(orderItemPage);

        List<Long> storeVariantIds = new ArrayList<>();
        for (OrderItem item : orderItems) {
            storeVariantIds.add(item.getStoreVariantId());
        }
        OrderQuoteApplyRequest orderQuoteApplyRequest = new OrderQuoteApplyRequest();
        orderQuoteApplyRequest.setOrderId(request.getOrderId());
        orderQuoteApplyRequest.setStoreId(order.getStoreId());
        orderQuoteApplyRequest.setCustomerId(session.getCustomerId());
        orderQuoteApplyRequest.setUserId(session.getId());
        orderQuoteApplyRequest.setStoreVariantId(storeVariantIds);
        return pmsFeignClient.orderQuoteApply(orderQuoteApplyRequest);
    }

//    @ApiOperation("airwallex导出")
//    @PostMapping("/airwallex")
    public BaseResponse airwallex(@RequestBody AirwallexRequest airwallexRequest) throws CustomerException {
        if (airwallexRequest == null || airwallexRequest.getBeginTime() == null || airwallexRequest.getEndTime() == null){
            throw new CustomerException(CustomerExceptionEnum.TIME_CANNOT_BE_EMPTY);
        }
        return  BaseResponse.success(orderItemService.airwallex(airwallexRequest));
    }
}
