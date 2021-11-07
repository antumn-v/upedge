package com.upedge.oms.modules.order.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.store.StoreVo;
import com.upedge.common.model.store.request.StoreApiRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.StoreOrder;
import com.upedge.oms.modules.order.request.*;
import com.upedge.oms.modules.order.response.StoreOrderListResponse;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.service.StoreOrderService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

/**
 * 
 *
 * @author author
 */
@Slf4j
@RestController
@RequestMapping("/storeOrder")
public class StoreOrderController {
    @Autowired
    private StoreOrderService storeOrderService;

    @Autowired
    OrderService orderService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;


    @PostMapping("/unrecognized/list")
    public BaseResponse storeUnrecognizedList(@RequestBody UnrecognizedStoreOrderListRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return  storeOrderService.unrecognizedStoreOrderList(request,session);
    }

    @PostMapping("/create/order")
    public BaseResponse createOrder(@RequestBody List<Long> ids){
        for (Long id : ids) {
            StoreOrder storeOrder = storeOrderService.selectByPrimaryKey(id);
            if (storeOrder == null){
                continue;
            }
            storeOrderService.completeStoreOrderItemDetail(storeOrder.getId());
            Order order =  orderService.createOrderByStoreOrder(storeOrder.getId());
            if(order != null){
                orderService.orderInitShipDetail(order.getId());
            }
        }
        return BaseResponse.success();
    }

    @PostMapping("/get/order")
    public BaseResponse getOrder(){
        List<Long> ids = storeOrderService.selectIdsByCreateTime();
        for (Long id : ids) {
            StoreOrder storeOrder = storeOrderService.selectByPrimaryKey(id);
            if (storeOrder == null){
                continue;
            }
            storeOrderService.completeStoreOrderItemDetail(storeOrder.getId());
            Order order =  orderService.createOrderByStoreOrder(storeOrder.getId());
            if(order != null){
                orderService.orderInitShipDetail(order.getId());
            }
        }
        return BaseResponse.success();
    }

    @PostMapping("/shopify/update")
    public BaseResponse updateShopifyOrder(@RequestBody StoreApiRequest request){
        StoreOrder storeOrder = storeOrderService.shopifyOrderUpdate(request);
        if (storeOrder == null){
            return BaseResponse.success();
        }
        storeOrderService.completeStoreOrderItemDetail(storeOrder.getId());
        Order order =  orderService.createOrderByStoreOrder(storeOrder.getId());
        if(order != null){
            orderService.orderInitShipDetail(order.getId());
        }
        return BaseResponse.success();
    }

    @PostMapping("/shoplazza/update")
    public BaseResponse updateShoplazzaOrder(@RequestBody StoreApiRequest request){
        StoreOrder storeOrder = storeOrderService.shoplazzaOrderUpdate(request);
        storeOrderService.completeStoreOrderItemDetail(storeOrder.getId());
        Order order =  orderService.createOrderByStoreOrder(storeOrder.getId());
        if(order != null){
            orderService.orderInitShipDetail(order.getId());
        }
        return BaseResponse.success();
    }

    @PostMapping("/woocommerce/update")
    public BaseResponse updateWoocommerceOrder(@RequestBody StoreApiRequest request){
        StoreOrder storeOrder = storeOrderService.woocommerceOrderUpdate(request);
        storeOrderService.completeStoreOrderItemDetail(storeOrder.getId());
        Order order =  orderService.createOrderByStoreOrder(storeOrder.getId());
        if(order != null){
            orderService.orderInitShipDetail(order.getId());
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE,storeOrder);
    }

    @ApiOperation("客户店铺订单数量")
    @PostMapping("/count")
    public BaseResponse storeOrderCount(){
        Session session = UserUtil.getSession(redisTemplate);
        Long count = storeOrderService.customerStoreOrderCount(session);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,count);
    }

    @ApiOperation("客户店铺订单金额")
    @PostMapping("/income")
    public BaseResponse storeOrderIncome(){
        Session session = UserUtil.getSession(redisTemplate);
        BigDecimal income = storeOrderService.customerStoreOrderIncome(session);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,income);
    }


    //===============================admin===================================

    /**
     * 客户店铺未关联订单
     * @return
     */
    @RequestMapping(value="/admin/disConnectList", method=RequestMethod.POST)
    public StoreOrderListResponse disConnectList(@RequestBody @Valid StoreOrderListRequest request) {
        return storeOrderService.disConnectList(request);
    }

    /**
     * admin所有订单 店铺订单数据
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/dataList", method=RequestMethod.POST)
    public StoreOrderListResponse dataList(@RequestBody @Valid StoreDataListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);

        return storeOrderService.dataList(request);
    }

    /**
     * 导出数据列表
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/exportList", method=RequestMethod.POST)
    public StoreOrderListResponse exportList(@RequestBody @Valid StoreDataListRequest request) {
        return storeOrderService.exportList(request);
    }

    /**
     * 根据店铺id查询 店铺下所有订单
     * @param storeId
     * @return
     */
    @PostMapping("/{storeId}/order")
    public BaseResponse storeOrderCount(@PathVariable("storeId") Long storeId){
        List<StoreVo> resultList =  storeOrderService.selectOrderListByStoreId(storeId);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,resultList);
    };

}