package com.upedge.sms.modules.overseaWarehouse.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.cart.request.CartVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.sms.modules.overseaWarehouse.dao.OverseaWarehouseServiceOrderDao;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderCreateRequest;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class OverseaWarehouseServiceOrderServiceImpl implements OverseaWarehouseServiceOrderService {

    @Autowired
    private OverseaWarehouseServiceOrderDao overseaWarehouseServiceOrderDao;

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
    public BaseResponse create(OverseaWarehouseServiceOrderCreateRequest request, Session session) {
        List<CartVo> cartVos = omsFeignClient.selectByIds(request.getCartIds(),0,session.getCustomerId());
        if (ListUtils.isEmpty(cartVos)){
            return BaseResponse.failed();
        }
        Long orderId = IdGenerate.nextId();

        List<OverseaWarehouseServiceOrderItem> orderItems = new ArrayList<>();
        for (CartVo cartVo : cartVos) {
            OverseaWarehouseServiceOrderItem orderItem = new OverseaWarehouseServiceOrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setId(IdGenerate.nextId());
            orderItem.setPrice(cartVo.getPrice());
            orderItem.setQuantity(cartVo.getQuantity());
        }


        OverseaWarehouseServiceOrder overseaWarehouseServiceOrder = new OverseaWarehouseServiceOrder();
        overseaWarehouseServiceOrder.setCustomerId(session.getCustomerId());
        overseaWarehouseServiceOrder.setId(orderId);

        return null;
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