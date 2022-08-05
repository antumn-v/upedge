package com.upedge.oms.modules.pick.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.pick.dao.OrderPickDao;
import com.upedge.oms.modules.pick.entity.OrderPick;
import com.upedge.oms.modules.pick.request.OrderPickCreateRequest;
import com.upedge.oms.modules.pick.request.OrderPickPreviewListRequest;
import com.upedge.oms.modules.pick.service.OrderPickService;
import com.upedge.oms.modules.pick.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class OrderPickServiceImpl implements OrderPickService {

    @Autowired
    private OrderPickDao orderPickDao;

    @Autowired
    OrderService orderService;

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
    public BaseResponse twicePickInfo(Long pickId) {

        OrderPick orderPick = selectByPrimaryKey(pickId);
        if (null == orderPick
                || orderPick.getPickType() != 2
        || orderPick.getPickState() != 0){
            return BaseResponse.failed();
        }

        List<OrderPickInfoVo> orderPickInfoVos = orderPickDao.selectOrderPickInfo(pickId);

        List<OrderTwicePickVo.VariantOrderId> variantOrderIds = new ArrayList<>();

        Map<Long, OrderTwicePickVo.VariantOrderId> map = new HashMap<>();
        for (OrderPickInfoVo orderPickInfoVo : orderPickInfoVos) {
            Long orderId = orderPickInfoVo.getOrderId();
            List<OrderItemPickInfoVo> itemPickInfoVos = orderPickInfoVo.getOrderItemPickInfoVos();
            for (OrderItemPickInfoVo itemPickInfoVo : itemPickInfoVos) {
                Long variantId = itemPickInfoVo.getVariantId();
                if (!map.containsKey(variantId)){
                    OrderTwicePickVo.VariantOrderId variantOrderId = new OrderTwicePickVo.VariantOrderId();
                    variantOrderId.setVariantId(variantId);
                    variantOrderId.setVariantSku(itemPickInfoVo.getVariantSku());
                    variantOrderId.setBarcode(itemPickInfoVo.getBarcode());
                    map.put(variantId,variantOrderId);
                }
                map.get(variantId).getOrderIds().add(orderId);
            }
        }

        map.forEach((aLong, variantOrderId) -> {
            variantOrderIds.add(variantOrderId);
        });

        OrderTwicePickVo orderTwicePickVo = new OrderTwicePickVo();
        orderTwicePickVo.setOrderPickInfoVos(orderPickInfoVos);
        orderTwicePickVo.setVariantOrderIds(variantOrderIds);

        return BaseResponse.success(orderTwicePickVo);
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

    @Transactional
    @Override
    public BaseResponse create(OrderPickCreateRequest request, Session session) {
        String key = "key:createOrderPickWave";
        boolean b = RedisUtil.lock(redisTemplate,key,10L,60 * 1000L);
        if (!b){
            return BaseResponse.failed();
        }
        switch (request.getPickType()){
            case 0:
                oneSkuOneQtyCreateWave(request.getShipMethodIds(),request.getSingleProductQuantity(),session.getId());
                break;
            case 1:
                oneSkuMultiQtyCreateWave(request.getShipMethodIds(),request.getSingleProductQuantity(),session.getId());
                break;
            case 2:
                multiSkuCreateWave(request.getShipMethodIds(),request.getSingleProductQuantity(),session.getId());
                break;
            default:
                return BaseResponse.failed();
        }
        RedisUtil.unLock(redisTemplate,key);
        return BaseResponse.success();

    }


    public void multiSkuCreateWave(List<Long> shipMethodIds,Integer size,Long operatorId){

        List<OrderPickQuantityVo> orderPickQuantityVos = orderPickDao.selectMultiSkuOrderQuantity(shipMethodIds, size);
        if (ListUtils.isEmpty(orderPickQuantityVos)){
            return;
        }
        List<Long> orderIds = new ArrayList<>();
        orderPickQuantityVos.forEach(orderPickQuantityVo -> {
            orderIds.add(orderPickQuantityVo.getOrderId());
        });

        Long pickId = IdGenerate.nextId();
        OrderPick orderPick = new OrderPick();
        orderPick.setPickType(0);
        orderPick.setId(pickId);
        orderPick.setPickState(0);
        orderPick.setOperatorId(operatorId);
        orderPick.setCreateTime(new Date());
        orderPick.setUpdateTime(new Date());
        orderPick.setSkuType(1);
        orderPick.setSkuQuantity(size);
        insert(orderPick);

        orderService.updateOrderPickState(orderIds,2,pickId);

        while (orderPickQuantityVos.size() == size){
            multiSkuCreateWave(shipMethodIds,size,operatorId);
        }
    }

    public void oneSkuOneQtyCreateWave(List<Long> shipMethodIds,Integer size,Long operatorId){

        List<OrderPickQuantityVo> orderPickQuantityVos = orderPickDao.selectOneSkuOneProductOrderQuantity(shipMethodIds, size);
        if (ListUtils.isEmpty(orderPickQuantityVos)){
            return;
        }
        List<Long> orderIds = new ArrayList<>();
        orderPickQuantityVos.forEach(orderPickQuantityVo -> {
            orderIds.add(orderPickQuantityVo.getOrderId());
        });

        Long pickId = IdGenerate.nextId();
        OrderPick orderPick = new OrderPick();
        orderPick.setPickType(0);
        orderPick.setId(pickId);
        orderPick.setOperatorId(operatorId);
        orderPick.setCreateTime(new Date());
        orderPick.setUpdateTime(new Date());
        orderPick.setSkuType(1);
        orderPick.setPickState(0);
        orderPick.setSkuQuantity(size);
        insert(orderPick);

        orderService.updateOrderPickState(orderIds,3,pickId);

        while (orderPickQuantityVos.size() == size){
            oneSkuOneQtyCreateWave(shipMethodIds,size,operatorId);
        }

    }

    public void oneSkuMultiQtyCreateWave(List<Long> shipMethodIds,Integer size,Long operatorId){

        List<OrderPickQuantityVo> orderPickQuantityVos = orderPickDao.selectOneSkuMultiProductOrderQuantity(shipMethodIds, size / 2 + 5);
        if (ListUtils.isEmpty(orderPickQuantityVos)){
            return;
        }
        List<Long> orderIds = new ArrayList<>();
        int skuQuantity = 0;
        for (OrderPickQuantityVo orderPickQuantityVo : orderPickQuantityVos) {
            skuQuantity += orderPickQuantityVo.getSkuQuantity();
            if (skuQuantity > size){
                break;
            }
            orderIds.add(orderPickQuantityVo.getOrderId());
        }
        if (ListUtils.isEmpty(orderIds)){
            return;
        }

        Long pickId = IdGenerate.nextId();
        OrderPick orderPick = new OrderPick();
        orderPick.setPickType(0);
        orderPick.setId(pickId);
        orderPick.setOperatorId(operatorId);
        orderPick.setCreateTime(new Date());
        orderPick.setUpdateTime(new Date());
        orderPick.setSkuType(1);
        orderPick.setPickState(0);
        orderPick.setSkuQuantity(skuQuantity);
        insert(orderPick);

        orderService.updateOrderPickState(orderIds,3,pickId);

        while (orderPickQuantityVos.size() == size){
            oneSkuMultiQtyCreateWave(shipMethodIds,size,operatorId);
        }
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