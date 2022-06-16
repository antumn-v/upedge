package com.upedge.oms.modules.orderShippingUnit.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.OrderType;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.TmsFeignClient;
import com.upedge.common.model.tms.ShippingUnitVo;
import com.upedge.common.utils.IdGenerate;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.orderShippingUnit.dao.OrderShippingUnitDao;
import com.upedge.oms.modules.orderShippingUnit.entity.OrderShippingUnit;
import com.upedge.oms.modules.orderShippingUnit.service.OrderShippingUnitService;
import com.upedge.oms.modules.orderShippingUnit.vo.OrderShippingUnitVo;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service
public class OrderShippingUnitServiceImpl implements OrderShippingUnitService {

    @Autowired
    private OrderShippingUnitDao orderShippingUnitDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    TmsFeignClient tmsFeignClient;

    @Autowired
    private WholesaleOrderService wholesaleOrderService;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        OrderShippingUnit record = new OrderShippingUnit();
        record.setId(id);
        return orderShippingUnitDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrderShippingUnit record) {
        return orderShippingUnitDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderShippingUnit record) {
        return orderShippingUnitDao.insert(record);
    }

    @Override
    public List<Long> selectOrderIdByOrderPaymentId(Long paymentId, Integer orderType) {
        return orderShippingUnitDao.selectOrderIdByOrderPaymentId(paymentId, orderType);
    }

    @Override
    public void updateOrderShipUnit(Long orderId, Long shippingUnitId) {
        // 删除原来的unit  并插入新的冗余
        delByOrderId(orderId, OrderType.NORMAL);
        if (shippingUnitId != null){
            BaseResponse shippingUnitResponse = tmsFeignClient.unitInfo(shippingUnitId);
            if (shippingUnitResponse.getCode() == ResultCode.SUCCESS_CODE && shippingUnitResponse.getData() != null){
                ShippingUnitVo shippingUnit = JSON.parseObject(JSON.toJSONString(shippingUnitResponse.getData()), ShippingUnitVo.class);
                OrderShippingUnit orderShippingUnit = new OrderShippingUnit();
                BeanUtils.copyProperties(shippingUnit,orderShippingUnit);
                orderShippingUnit.setOrderId(orderId);
                orderShippingUnit.setOrderType(OrderType.NORMAL);
                orderShippingUnit.setId(IdGenerate.nextId());
                orderShippingUnit.setShipUnitId(shippingUnit.getId());
                orderShippingUnit.setCreateTime(new Date());
                orderShippingUnitDao.insert(orderShippingUnit);
            }
        }
    }

    @Override
    public void deleteByShipUnitId(Long shipUnitId) {
        orderShippingUnitDao.deleteUnPaidOrderUnitByShipUnitId(shipUnitId);
    }

    /**
     *
     */
    public OrderShippingUnit selectByPrimaryKey(Long id){
        OrderShippingUnit record = new OrderShippingUnit();
        record.setId(id);
        return orderShippingUnitDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OrderShippingUnit record) {
        return orderShippingUnitDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OrderShippingUnit record) {
        return orderShippingUnitDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OrderShippingUnit> select(Page<OrderShippingUnit> record){
        record.initFromNum();
        return orderShippingUnitDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OrderShippingUnit> record){
        return orderShippingUnitDao.count(record);
    }

    @Override
    public OrderShippingUnitVo selectByOrderId(Long orderId, int orderType) {

        return orderShippingUnitDao.selectByOrderId(orderId,orderType);
    }

    @Override
    public int delByOrderId(Long orderId, int orderType) {
        OrderShippingUnit record = new OrderShippingUnit();
        record.setOrderId(orderId);
        record.setOrderType(orderType);
        return orderShippingUnitDao.delete(record);
    }



    @Override
    public void delByProductId(Long productId, int orderType) {
        orderShippingUnitDao.delByProductId(productId , orderType);
    }

    @Override
    public void delByVariantId(Long variantId, int orderType) {
        orderShippingUnitDao.delByVariantId(variantId , orderType);
    }
}