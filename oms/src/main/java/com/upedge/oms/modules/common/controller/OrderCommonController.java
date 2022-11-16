package com.upedge.oms.modules.common.controller;


import com.upedge.common.base.BaseResponse;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.oms.order.OrderStockClearRequest;
import com.upedge.common.model.order.OrderItemQuantityDto;
import com.upedge.common.model.order.request.OrderStockStateUpdateRequest;
import com.upedge.common.model.order.vo.UplodaSaiheOnMqVo;
import com.upedge.common.model.pms.quote.CustomerProductQuoteVo;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.common.service.MqOnSaiheService;
import com.upedge.oms.modules.common.service.OrderCommonService;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.order.service.OrderPayService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.scheduler.PackageScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderCommon")
public class OrderCommonController {

    @Autowired
    private MqOnSaiheService mqOnSaiheService;

    @Autowired
    private OrderCommonService orderCommonService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderPayService orderPayService;

    @Autowired
    PackageScheduler packageScheduler;

    @PostMapping("/jsonTest")
    public BaseResponse jsonTest(){
        orderCommonService.refreshReferrerCommission();
//        orderPayService.checkOrderAccountLog();
        return BaseResponse.success();
    }

    /**
     * 刷新赛盒发货状态
     * @param id
     * @param orderType
     * @return
     */
    @GetMapping(value = "refreshTrackingState/{id}")
    public BaseResponse refreshTrackingState(@PathVariable String id, @RequestParam Integer orderType) throws CustomerException {
        boolean res=orderCommonService.synRefundTrackingState(id,orderType);
        if(res){
            return BaseResponse.success("刷新赛盒发货状态成功");
        }else {
            return BaseResponse.failed("刷新赛盒发货状态失败");
        }
    }


    @PostMapping("/uploadPaymentIdOnMq")
    public void uploadPaymentIdOnMq(@RequestBody UplodaSaiheOnMqVo uplodaSaiheOnMq){
        mqOnSaiheService.uploadPaymentIdOnMq(uplodaSaiheOnMq.getPaymentId(), uplodaSaiheOnMq.getOrderType());
    }

    @PostMapping("/testUpload")
    public BaseResponse testUpload(@RequestBody List<Long> ids){
        for (Long id : ids) {
            Order order = orderService.selectByPrimaryKey(id);
            if (order!= null){
                if (order.getSaiheOrderCode() != null){
//                    SaiheService.cancelOrderInfo(order.getSaiheOrderCode());
                    orderService.updateSaiheOrderCode(String.valueOf(id),null);
                }
                orderService.importOrderToSaihe(id);
            }
        }
        return BaseResponse.success();
    }


    @PostMapping("/checkStock")
    public void checkStock(@RequestBody OrderItemQuantityDto orderItemQuantityDto){
        orderPayService.sendCheckOrderStockMessage(orderItemQuantityDto);
    }

    @PostMapping("/updateStockState")
    public int updateStockState(@RequestBody OrderStockStateUpdateRequest request){
//        return orderService.updateStockState(request.getOrderId(), request.getStockState());
        return orderService.updateStockState(request.getOrderId(), request.getItemQuantityVos());
    }


    @PostMapping("/stockClear")
    public BaseResponse itemStockClear(@RequestBody OrderStockClearRequest request){
        orderItemService.updateLockedQuantityClear(request);
        return BaseResponse.success();
    }

    @PostMapping("/updateQuoteDetail")
    public void itemUpdateQuoteDetail(@RequestBody List<CustomerProductQuoteVo> customerProductQuoteVos){
        if (ListUtils.isNotEmpty(customerProductQuoteVos)){
            for (CustomerProductQuoteVo customerProductQuoteVo : customerProductQuoteVos) {
                try {
                    if (customerProductQuoteVo.getStoreParentVariantId() == null
                            || customerProductQuoteVo.getStoreParentVariantId().equals(0L)){
                        orderItemService.updateItemQuoteDetail(customerProductQuoteVo);
                    }else {
                        //组合产品更新报价信息
                        orderItemService.updateSplitVariantItemQuoteDetail(customerProductQuoteVo);
                    }
                    orderItemService.updatePaidOrderItemQuoteDetail(customerProductQuoteVo);
                } catch (CustomerException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
