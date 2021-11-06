package com.upedge.oms.async;

import com.upedge.common.constant.OrderType;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.dao.OrderDao;
import com.upedge.oms.modules.order.dao.OrderItemDao;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.service.OrderProfitService;
import com.upedge.oms.modules.order.vo.AppOrderVo;
import com.upedge.oms.modules.sales.dao.CustomerProductSalesLogDao;
import com.upedge.oms.modules.sales.entity.CustomerProductSalesLog;
import com.upedge.oms.modules.stock.service.CustomerStockRecordService;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderItemDao;
import com.upedge.oms.modules.wholesale.entity.WholesaleOrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderAsyncTask {

    @Autowired
    CustomerStockRecordService customerStockRecordService;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    WholesaleOrderItemDao wholesaleOrderItemDao;

    @Autowired
    CustomerProductSalesLogDao customerProductSalesLogDao;

    @Autowired
    OrderProfitService orderProfitService;

    @Autowired
    OrderDao orderDao;

    /**
     * 保存订单项目库存记录
     * @param paymentId
     * @param customerId
     * @param orderType
     */
    @Async
    public void saveOrderItemStockRecord(Long paymentId, Long customerId, Integer orderType){
        customerStockRecordService.saveDischargeStockRecordByPaymentId(customerId,paymentId,orderType);
    }

    /**
     *保存产品销售记录
     * @param paymentId
     * @param orderType
     * @param customerId
     */
    @Async
    public void saveProductSaleRecord(Long paymentId, Integer orderType, Long customerId){
        Date date = new Date();
        List<CustomerProductSalesLog> records = new ArrayList<>();
        switch (orderType){
            case OrderType
                    .NORMAL:
                List<OrderItem> items = orderItemDao.selectItemByPaymentId(paymentId);
                if(ListUtils.isEmpty(items)){
                    return;
                }
                items.forEach(orderItem -> {
                    CustomerProductSalesLog record = new CustomerProductSalesLog();
                    record.setCreateTime(date);
                    record.setCustomerId(customerId);
                    record.setOrderType(orderType);
                    record.setOrderId(orderItem.getOrderId());
                    record.setOrderItemId(orderItem.getId());
                    record.setQuantity(orderItem.getQuantity());
                    record.setVariantId(orderItem.getAdminVariantId());
                    record.setProductId(orderItem.getAdminProductId());
                    record.setState(1);
                    records.add(record);
                });
                break;
            case OrderType.WHOLESALE:
                List<WholesaleOrderItem> itemList = wholesaleOrderItemDao.selectByOrderPaymentId(paymentId);
                if (ListUtils.isEmpty(itemList)){
                    return;
                }
                itemList.forEach(wholesaleOrderItem -> {
                    CustomerProductSalesLog record = new CustomerProductSalesLog();
                    record.setCreateTime(date);
                    record.setCustomerId(customerId);
                    record.setOrderId(wholesaleOrderItem.getOrderId());
                    record.setOrderItemId(wholesaleOrderItem.getId());
                    record.setQuantity(wholesaleOrderItem.getQuantity());
                    record.setOrderType(orderType);
                    record.setVariantId(wholesaleOrderItem.getAdminVariantId());
                    record.setProductId(wholesaleOrderItem.getAdminProductId());
                    record.setState(1);
                    records.add(record);
                });
                break;
            default:
                return;
        }
        if (ListUtils.isNotEmpty(records)){
            customerProductSalesLogDao.insertByBatch(records);
        }
    }

    @Async
    public void updateOrderProfitByPaymentId(Long paymentId){
        List<AppOrderVo> orderVos = orderDao.selectPayOrderListByPaymentId(paymentId);
        if(ListUtils.isNotEmpty(orderVos)){
            for (AppOrderVo orderVo : orderVos) {
                if(orderVo.getPayState() == 1){
                    orderProfitService.updateOrderProfit(orderVo.getId());
                }
            }
        }
    }

}
