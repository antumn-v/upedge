package com.upedge.oms.modules.pick.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.pick.dao.OrderPickDao;
import com.upedge.oms.modules.pick.entity.OrderPick;
import com.upedge.oms.modules.pick.request.OrderPickCreateRequest;
import com.upedge.oms.modules.pick.request.OrderPickPreviewListRequest;
import com.upedge.oms.modules.pick.service.OrderPickService;
import com.upedge.oms.modules.pick.vo.OrderPickPreviewVo;
import com.upedge.oms.modules.pick.vo.OrderPickWaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OrderPickServiceImpl implements OrderPickService {

    @Autowired
    private OrderPickDao orderPickDao;

    @Autowired
    RedisTemplate redisTemplate;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        OrderPick record = new OrderPick();
        record.setId(id);
        return orderPickDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrderPick record) {
        return orderPickDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(OrderPick record) {
        return orderPickDao.insert(record);
    }

    @Override
    public BaseResponse previewList(OrderPickPreviewListRequest request) {
        List<OrderPickPreviewVo> orderPickPreviewVos = orderPickDao.countOrderPickPreview(request);

        Map<Integer, OrderPickWaveVo> map = new HashMap<>();
        a:
        for (OrderPickPreviewVo orderPickPreviewVo : orderPickPreviewVos) {

            OrderPickWaveVo orderPickWaveVo = map.get(orderPickPreviewVo.getPickType());
            if (null == orderPickWaveVo){
                orderPickWaveVo = new OrderPickWaveVo();
                orderPickWaveVo.setPickType(orderPickPreviewVo.getPickType());
                map.put(orderPickPreviewVo.getPickType(),orderPickWaveVo);
            }
            ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD,String.valueOf(orderPickPreviewVo.getShipMethodId()));
            if (null == shippingMethodRedis.getTrackingCompany()){
                shippingMethodRedis.setTrackingCompany("默认");
            }
            List<OrderPickWaveVo.ShipCompanyPickVo> shipCompanyPickVos = orderPickWaveVo.getShipCompanyPickVos();
            for (OrderPickWaveVo.ShipCompanyPickVo shipCompanyPickVo : shipCompanyPickVos) {
                if (shipCompanyPickVo.getCompany().equals(shippingMethodRedis.getTrackingCompany())){
                    shipCompanyPickVo.getOrderPickPreviewVos().add(orderPickPreviewVo);
                    shipCompanyPickVo.setTotal(shipCompanyPickVo.getTotal() + orderPickPreviewVo.getTotal());
                    continue a;
                }
            }
            OrderPickWaveVo.ShipCompanyPickVo shipCompanyPickVo = new OrderPickWaveVo.ShipCompanyPickVo();
            shipCompanyPickVo.setCompany(shippingMethodRedis.getTrackingCompany());
            shipCompanyPickVo.setTotal(orderPickPreviewVo.getTotal());
            orderPickPreviewVo.setShipMethodName(shippingMethodRedis.getName());
            orderPickPreviewVo.setShipMethodDesc(shippingMethodRedis.getDesc());
            shipCompanyPickVo.getOrderPickPreviewVos().add(orderPickPreviewVo);
            orderPickWaveVo.getShipCompanyPickVos().add(shipCompanyPickVo);
        }

        List<OrderPickWaveVo> orderPickWaveVos = new ArrayList<>();
        for (Map.Entry<Integer, OrderPickWaveVo> m : map.entrySet()){
            orderPickWaveVos.add(m.getValue());
        }

        return BaseResponse.success(orderPickWaveVos);
    }

    @Override
    public BaseResponse create(OrderPickCreateRequest request, Session session) {

        return null;
    }

    /**
     *
     */
    public OrderPick selectByPrimaryKey(Long id){
        OrderPick record = new OrderPick();
        record.setId(id);
        return orderPickDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OrderPick record) {
        return orderPickDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OrderPick record) {
        return orderPickDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OrderPick> select(Page<OrderPick> record){
        record.initFromNum();
        return orderPickDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OrderPick> record){
        return orderPickDao.count(record);
    }

}