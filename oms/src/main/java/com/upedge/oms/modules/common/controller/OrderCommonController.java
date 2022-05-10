package com.upedge.oms.modules.common.controller;


import com.upedge.common.base.BaseResponse;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.order.vo.UplodaSaiheOnMqVo;
import com.upedge.oms.modules.common.service.MqOnSaiheService;
import com.upedge.oms.modules.common.service.OrderCommonService;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.order.service.OrderPayService;
import com.upedge.oms.modules.order.service.OrderService;
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

    @PostMapping("/jsonTest")
    public BaseResponse jsonTest(){
        orderPayService.checkOrderAccountLog();
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
    public   void uploadPaymentIdOnMq(@RequestBody UplodaSaiheOnMqVo uplodaSaiheOnMq){
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




}
