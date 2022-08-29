package com.upedge.oms.modules.sales.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.utils.ListUtils;
import com.upedge.oms.modules.order.dao.OrderItemDao;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.order.vo.OrderPickTypeVo;
import com.upedge.oms.modules.sales.dao.CustomerProductSalesLogDao;
import com.upedge.oms.modules.sales.entity.CustomerProductSalesLog;
import com.upedge.oms.modules.sales.service.CustomerProductSalesLogService;
import com.upedge.oms.modules.wholesale.dao.WholesaleOrderItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class CustomerProductSalesLogServiceImpl implements CustomerProductSalesLogService {

    @Autowired
    private CustomerProductSalesLogDao customerProductSalesLogDao;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    WholesaleOrderItemDao wholesaleOrderItemDao;

    @Autowired
    OrderService orderService;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        CustomerProductSalesLog record = new CustomerProductSalesLog();
        record.setId(id);
        return customerProductSalesLogDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(CustomerProductSalesLog record) {
        return customerProductSalesLogDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(CustomerProductSalesLog record) {
        return customerProductSalesLogDao.insert(record);
    }

    @Override
    public void saveProductSaleRecord(Long paymentId, Integer orderType, Long customerId, Date date) {
        List<CustomerProductSalesLog> records = new ArrayList<>();
        Map<Long, OrderPickTypeVo> map = new HashMap<>();

        List<OrderItem> items = orderItemDao.selectItemByPaymentId(paymentId);
        if(ListUtils.isEmpty(items)){
            return;
        }
        items.forEach(orderItem -> {
            if (!map.containsKey(orderItem.getOrderId())){
                map.put(orderItem.getOrderId(), new OrderPickTypeVo());
            }
            map.get(orderItem.getOrderId()).getItems().add(orderItem);
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

//        switch (orderType){
//            case OrderType.NORMAL:
//
//                break;
//            case OrderType.WHOLESALE:
//                List<WholesaleOrderItem> itemList = wholesaleOrderItemDao.selectByOrderPaymentId(paymentId);
//                if (ListUtils.isEmpty(itemList)){
//                    return;
//                }
//                itemList.forEach(wholesaleOrderItem -> {
//                    CustomerProductSalesLog record = new CustomerProductSalesLog();
//                    record.setCreateTime(date);
//                    record.setCustomerId(customerId);
//                    record.setOrderId(wholesaleOrderItem.getOrderId());
//                    record.setOrderItemId(wholesaleOrderItem.getId());
//                    record.setQuantity(wholesaleOrderItem.getQuantity());
//                    record.setOrderType(orderType);
//                    record.setVariantId(wholesaleOrderItem.getAdminVariantId());
//                    record.setProductId(wholesaleOrderItem.getAdminProductId());
//                    record.setState(1);
//                    records.add(record);
//                });
//                break;
//            default:
//                return;
//        }
        if (ListUtils.isNotEmpty(records)){
            customerProductSalesLogDao.insertByBatch(records);
        }

        for (Map.Entry<Long,OrderPickTypeVo> m:map.entrySet()){
            OrderPickTypeVo orderPickTypeVo = m.getValue();
            List<OrderItem> orderItems = orderPickTypeVo.getItems();
            if (orderItems.size() > 1){
                Set<Long> adminVariantIds = new HashSet<>();
                for (OrderItem orderItem : orderItems) {
                    adminVariantIds.add(orderItem.getAdminVariantId());
                }
                if (adminVariantIds.size() > 1){
                    orderPickTypeVo.setPickType(2);
                }else {
                    orderPickTypeVo.setPickType(1);
                }

            }else {
                int totalQuantity = orderItems.stream().mapToInt(OrderItem::getQuantity).sum();
                if (totalQuantity > 1){
                    orderPickTypeVo.setPickType(1);
                }else {
                    orderPickTypeVo.setPickType(0);
                }
            }
            orderService.updatePickType(m.getKey(),orderPickTypeVo.getPickType());
        }
    }

    /**
     *
     */
    public CustomerProductSalesLog selectByPrimaryKey(Long id){
        CustomerProductSalesLog record = new CustomerProductSalesLog();
        record.setId(id);
        return customerProductSalesLogDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(CustomerProductSalesLog record) {
        return customerProductSalesLogDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(CustomerProductSalesLog record) {
        return customerProductSalesLogDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<CustomerProductSalesLog> select(Page<CustomerProductSalesLog> record){
        record.initFromNum();
        return customerProductSalesLogDao.select(record);
    }

    /**
    *
    */
    public long count(Page<CustomerProductSalesLog> record){
        return customerProductSalesLogDao.count(record);
    }

}