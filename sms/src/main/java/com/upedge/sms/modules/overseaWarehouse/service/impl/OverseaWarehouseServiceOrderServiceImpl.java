package com.upedge.sms.modules.overseaWarehouse.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.cart.request.CartVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.sms.modules.center.entity.ServiceOrder;
import com.upedge.sms.modules.center.service.ServiceOrderService;
import com.upedge.sms.modules.overseaWarehouse.dao.OverseaWarehouseServiceOrderDao;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderFreight;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderCreateRequest;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderFreightService;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderItemService;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderService;
import com.upedge.sms.modules.overseaWarehouse.vo.OverseaWarehouseServiceOrderVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class OverseaWarehouseServiceOrderServiceImpl implements OverseaWarehouseServiceOrderService {

    @Autowired
    private OverseaWarehouseServiceOrderDao overseaWarehouseServiceOrderDao;

    @Autowired
    OverseaWarehouseServiceOrderItemService overseaWarehouseServiceOrderItemService;

    @Autowired
    OverseaWarehouseServiceOrderFreightService overseaWarehouseServiceOrderFreightService;

    @Autowired
    ServiceOrderService serviceOrderService;

    @Autowired
    OmsFeignClient omsFeignClient;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        OverseaWarehouseServiceOrder record = new OverseaWarehouseServiceOrder();
        record.setId(id);
        return overseaWarehouseServiceOrderDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OverseaWarehouseServiceOrder record) {
        return overseaWarehouseServiceOrderDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OverseaWarehouseServiceOrder record) {
        return overseaWarehouseServiceOrderDao.insert(record);
    }

    @Override
    public OverseaWarehouseServiceOrderVo orderDetail(Long orderId) {
        OverseaWarehouseServiceOrder overseaWarehouseServiceOrder = selectByPrimaryKey(orderId);
        if (null == overseaWarehouseServiceOrder){
            return null;
        }
        OverseaWarehouseServiceOrderVo overseaWarehouseServiceOrderVo = new OverseaWarehouseServiceOrderVo();
        BeanUtils.copyProperties(overseaWarehouseServiceOrder,overseaWarehouseServiceOrderVo);

        List<OverseaWarehouseServiceOrderItem> orderItems = overseaWarehouseServiceOrderItemService.selectByOrderId(orderId);
        overseaWarehouseServiceOrderVo.setOrderItems(orderItems);

        List<OverseaWarehouseServiceOrderFreight> orderFreights = overseaWarehouseServiceOrderFreightService.selectByOrderId(orderId);
        overseaWarehouseServiceOrderVo.setOrderFreights(orderFreights);
        return overseaWarehouseServiceOrderVo;
    }

    @Transactional
    @Override
    public BaseResponse create(OverseaWarehouseServiceOrderCreateRequest request, Session session) {
        List<CartVo> cartVos = omsFeignClient.selectByIds(request.getCartIds(),0,session.getCustomerId());
        if (ListUtils.isEmpty(cartVos)){
            return BaseResponse.failed();
        }
        Long orderId = IdGenerate.nextId();
        BigDecimal productAmount = BigDecimal.ZERO;
        BigDecimal totalWeight = BigDecimal.ZERO;
        BigDecimal totalVolume = BigDecimal.ZERO;
        List<OverseaWarehouseServiceOrderItem> orderItems = new ArrayList<>();
        for (CartVo cartVo : cartVos) {
            BigDecimal quantity = new BigDecimal(cartVo.getQuantity());
            OverseaWarehouseServiceOrderItem orderItem = new OverseaWarehouseServiceOrderItem();
            BeanUtils.copyProperties(cartVo,orderItem);
            orderItem.setOrderId(orderId);
            orderItem.setId(IdGenerate.nextId());
            orderItems.add(orderItem);
            productAmount = productAmount.add(quantity.multiply(cartVo.getPrice()));
            totalWeight = totalWeight.add(quantity.multiply(cartVo.getVariantWeight()));
            totalVolume = totalVolume.add(quantity.multiply(cartVo.getVariantVolume()));
        }
        overseaWarehouseServiceOrderItemService.insertByBatch(orderItems);

        OverseaWarehouseServiceOrder overseaWarehouseServiceOrder = new OverseaWarehouseServiceOrder();
        overseaWarehouseServiceOrder.setCustomerId(session.getCustomerId());
        overseaWarehouseServiceOrder.setId(orderId);
        overseaWarehouseServiceOrder.setTotalWeight(totalWeight);
        overseaWarehouseServiceOrder.setTotalVolume(totalVolume);
        overseaWarehouseServiceOrder.setProductAmount(productAmount);
        overseaWarehouseServiceOrderDao.insert(overseaWarehouseServiceOrder);

        List<OverseaWarehouseServiceOrderFreight> orderFreights = new ArrayList<>();
        List<Integer> shipTypes = request.getLogistics();;
        for (Integer shipType : shipTypes) {
            OverseaWarehouseServiceOrderFreight overseaWarehouseServiceOrderFreight = new OverseaWarehouseServiceOrderFreight();
            overseaWarehouseServiceOrderFreight.setOrderId(orderId);
            overseaWarehouseServiceOrderFreight.setShipType(shipType);
            orderFreights.add(overseaWarehouseServiceOrderFreight);
        }
        overseaWarehouseServiceOrderFreightService.insertByBatch(orderFreights);

        ServiceOrder serviceOrder = new ServiceOrder();
        serviceOrder.setId(IdGenerate.nextId());
        serviceOrder.setRelateId(orderId);
        serviceOrder.setServiceState(0);
        serviceOrder.setCustomerId(session.getCustomerId());
        serviceOrder.setCreateTime(new Date());
        serviceOrder.setPayState(0);
        serviceOrder.setRefundState(0);
        serviceOrder.setServiceType(0);
        serviceOrder.setUpdateTime(new Date());
        serviceOrderService.insert(serviceOrder);

        return BaseResponse.success();
    }

    /**
     *
     */
    public OverseaWarehouseServiceOrder selectByPrimaryKey(Long id){
        OverseaWarehouseServiceOrder record = new OverseaWarehouseServiceOrder();
        record.setId(id);
        return overseaWarehouseServiceOrderDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OverseaWarehouseServiceOrder record) {
        return overseaWarehouseServiceOrderDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OverseaWarehouseServiceOrder record) {
        return overseaWarehouseServiceOrderDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OverseaWarehouseServiceOrder> select(Page<OverseaWarehouseServiceOrder> record){
        record.initFromNum();
        return overseaWarehouseServiceOrderDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OverseaWarehouseServiceOrder> record){
        return overseaWarehouseServiceOrderDao.count(record);
    }

}