package com.upedge.oms.modules.pick.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.pick.dao.OrderPickDao;
import com.upedge.oms.modules.pick.entity.OrderPick;
import com.upedge.oms.modules.pick.request.OrderPickCreateRequest;
import com.upedge.oms.modules.pick.request.OrderPickPreviewListRequest;
import com.upedge.oms.modules.pick.request.TwicePickSubmitRequest;
import com.upedge.oms.modules.pick.service.OrderPickService;
import com.upedge.oms.modules.pick.vo.*;
import org.springframework.beans.BeanUtils;
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
    OrderItemService orderItemService;

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
    public BaseResponse printPickInfo(Long pickId, Session session) {

        List<OrderPickInfoVo> orderPickInfoVoList = orderPickDao.selectOrderPickInfo(pickId);

        if (ListUtils.isEmpty(orderPickInfoVoList)){
            return BaseResponse.failed();
        }

        Integer quantity = 0;
        Set<Long> variantIds = new HashSet<>();

        Map<Long,OrderItemPickInfoVo> map = new HashMap<>();

        for (OrderPickInfoVo orderPickInfoVo : orderPickInfoVoList) {
            List<OrderItemPickInfoVo> orderItemPickInfoVos = orderPickInfoVo.getOrderItemPickInfoVos();
            for (OrderItemPickInfoVo orderItemPickInfoVo : orderItemPickInfoVos) {
                quantity += orderItemPickInfoVo.getQuantity();
                variantIds.add(orderItemPickInfoVo.getVariantId());
                if (map.containsKey(orderItemPickInfoVo.getVariantId())){
                    OrderItemPickInfoVo itemPickInfoVo = map.get(orderItemPickInfoVo.getVariantId());
                    orderItemPickInfoVo.setQuantity(orderItemPickInfoVo.getQuantity() + itemPickInfoVo.getQuantity());
                    orderItemPickInfoVo.setPickedQuantity(orderItemPickInfoVo.getPickedQuantity() + itemPickInfoVo.getPickedQuantity());
                }
                map.put(orderItemPickInfoVo.getVariantId(),orderItemPickInfoVo);
            }
        }

        List<OrderItemPickInfoVo> orderItemPickInfoVos = new ArrayList<>();
        map.forEach((variantId,item)-> {
            orderItemPickInfoVos.add(item);
        });

        OrderPrintVo orderPrintVo = new OrderPrintVo();
        orderPrintVo.setOrderItemPickInfoVos(orderItemPickInfoVos);
        orderPrintVo.setSkuQuantity(quantity);
        orderPrintVo.setSkuType(variantIds.size());
        orderPrintVo.setPackageCount(orderPickInfoVoList.size());
        return BaseResponse.success(orderPrintVo);
    }

    @Transactional
    @Override
    public BaseResponse twicePickSubmit(TwicePickSubmitRequest request, Session session) {
        Long pickId = request.getPickId();
        List<OrderPickInfoVo> orderPickInfoVos = request.getOrderPickInfoVos();

        Map<Long,OrderItemPickInfoVo> map = new HashMap<>();
        List<OrderPickInfoVo> orderPickInfoVoList = orderPickDao.selectOrderPickInfo(pickId);
        for (OrderPickInfoVo orderPickInfoVo : orderPickInfoVoList) {
            List<OrderItemPickInfoVo> itemPickInfoVos = orderPickInfoVo.getOrderItemPickInfoVos();
            for (OrderItemPickInfoVo itemPickInfoVo : itemPickInfoVos) {
                map.put(itemPickInfoVo.getItemId(),itemPickInfoVo);
            }
        }

        boolean b = true;

        List<OrderItem> updatePickedQuantity = new ArrayList<>();
        for (OrderPickInfoVo orderPickInfoVo : orderPickInfoVos) {

            List<OrderItemPickInfoVo> itemPickInfoVos = orderPickInfoVo.getOrderItemPickInfoVos();
            for (OrderItemPickInfoVo itemPickInfoVo : itemPickInfoVos) {

                OrderItemPickInfoVo orderItemPickInfoVo = map.get(itemPickInfoVo.getItemId());
                Integer pickedQuantity = itemPickInfoVo.getPickedQuantity();
                if (!itemPickInfoVo.getQuantity().equals(orderItemPickInfoVo.getPickedQuantity())){
                    b = false;
                }
                if (pickedQuantity != orderItemPickInfoVo.getPickedQuantity()){
                    OrderItem orderItem = new OrderItem();
                    orderItem.setId(orderItemPickInfoVo.getItemId());
                    orderItem.setPickedQuantity(pickedQuantity);
                    updatePickedQuantity.add(orderItem);
                }
            }
        }
        if (b){
            OrderPick orderPick = new OrderPick();
            orderPick.setPickState(OrderPick.TO_BE_PACKED);
            orderPick.setId(pickId);
            orderPick.setUpdateTime(new Date());
            updateByPrimaryKeySelective(orderPick);
        }
        orderItemService.batchUpdatePickedQuantity(updatePickedQuantity);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse twicePickInfo(Long pickId) {

        OrderPick orderPick = selectByPrimaryKey(pickId);
        if (null == orderPick
                || orderPick.getPickType() != 2
        || orderPick.getPickState() < OrderPick.TO_BE_PICKED
        || orderPick.getPickState() > OrderPick.TWICE_PICK){
            return BaseResponse.failed();
        }

        List<OrderPickInfoVo> orderPickInfoVos = orderPickDao.selectOrderPickInfo(pickId);

        List<OrderTwicePickVo.VariantOrderId> variantOrderIds = new ArrayList<>();
        int i = 1;
        Map<Long, OrderTwicePickVo.VariantOrderId> map = new HashMap<>();
        for (OrderPickInfoVo orderPickInfoVo : orderPickInfoVos) {
            orderPickInfoVo.setSeq(i++);
            Long orderId = orderPickInfoVo.getOrderId();
            List<OrderItemPickInfoVo> itemPickInfoVos = orderPickInfoVo.getOrderItemPickInfoVos();
            for (OrderItemPickInfoVo itemPickInfoVo : itemPickInfoVos) {
                Long variantId = itemPickInfoVo.getVariantId();
                if (!map.containsKey(variantId)){
                    OrderTwicePickVo.VariantOrderId variantOrderId = new OrderTwicePickVo.VariantOrderId();
                    BeanUtils.copyProperties(itemPickInfoVo,variantOrderId);
                    map.put(variantId,variantOrderId);
                }else {
                    OrderTwicePickVo.VariantOrderId variantOrderId = map.get(variantId);
                    variantOrderId.setQuantity(variantOrderId.getQuantity() + itemPickInfoVo.getQuantity());
                    variantOrderId.setPickedQuantity(variantOrderId.getPickedQuantity() + itemPickInfoVo.getPickedQuantity());
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

        orderPick = new OrderPick();
        orderPick.setPickState(OrderPick.TWICE_PICK);
        orderPick.setId(pickId);
        orderPick.setUpdateTime(new Date());
        updateByPrimaryKeySelective(orderPick);

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
                multiSkuCreateWave(request.getShipMethodIds(),request.getMixedProductQuantity(),session.getId());
                break;
            default:
                return BaseResponse.failed();
        }
        RedisUtil.unLock(redisTemplate,key);
        return BaseResponse.success();
    }


    public int multiSkuCreateWave(List<Long> shipMethodIds,Integer size,Long operatorId){

        List<OrderPickQuantityVo> orderPickQuantityVos = orderPickDao.selectMultiSkuOrderQuantity(shipMethodIds, size);
        if (ListUtils.isEmpty(orderPickQuantityVos)){
            return 0;
        }
        Set<Long> variantIds = new HashSet<>();

        List<Long> orderIds = new ArrayList<>();
        orderPickQuantityVos.forEach(orderPickQuantityVo -> {
            orderIds.add(orderPickQuantityVo.getOrderId());

        });

        List<OrderItem> orderItems = orderItemService.selectByOrderIds(orderIds);
        orderItems.forEach(orderItem -> {
            variantIds.add(orderItem.getAdminVariantId());
        });

        Long pickId = IdGenerate.nextId();
        OrderPick orderPick = new OrderPick();
        orderPick.setPickType(2);
        orderPick.setId(pickId);
        orderPick.setPickState(OrderPick.TO_BE_PICKED);
        orderPick.setOperatorId(operatorId);
        orderPick.setCreateTime(new Date());
        orderPick.setUpdateTime(new Date());
        orderPick.setSkuType(variantIds.size());
        orderPick.setSkuQuantity(size);
        insert(orderPick);

        orderService.updateOrderPickState(orderIds,2,pickId);

        while (orderPickQuantityVos.size() == size){
            int i = multiSkuCreateWave(shipMethodIds,size,operatorId);
            if (i == 0){
                return i;
            }
        }
        return orderPickQuantityVos.size();
    }

    public int oneSkuOneQtyCreateWave(List<Long> shipMethodIds,Integer size,Long operatorId){

        List<OrderPickQuantityVo> orderPickQuantityVos = orderPickDao.selectOneSkuOneProductOrderQuantity(shipMethodIds, size);
        if (ListUtils.isEmpty(orderPickQuantityVos)){
            return 0;
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
        orderPick.setPickState(OrderPick.TO_BE_PACKED);
        orderPick.setSkuQuantity(size);
        insert(orderPick);

        orderService.updateOrderPickState(orderIds,3,pickId);

        while (orderPickQuantityVos.size() == size){
            int i = oneSkuOneQtyCreateWave(shipMethodIds,size,operatorId);
            if (i == 0){
                return i;
            }
        }
        return orderPickQuantityVos.size();

    }

    public int oneSkuMultiQtyCreateWave(List<Long> shipMethodIds,Integer size,Long operatorId){

        List<OrderPickQuantityVo> orderPickQuantityVos = orderPickDao.selectOneSkuMultiProductOrderQuantity(shipMethodIds, size / 2 + 5);
        if (ListUtils.isEmpty(orderPickQuantityVos)){
            return 0;
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
            return 0;
        }

        Long pickId = IdGenerate.nextId();
        OrderPick orderPick = new OrderPick();
        orderPick.setPickType(1);
        orderPick.setId(pickId);
        orderPick.setOperatorId(operatorId);
        orderPick.setCreateTime(new Date());
        orderPick.setUpdateTime(new Date());
        orderPick.setSkuType(1);
        orderPick.setPickState(OrderPick.TO_BE_PACKED);
        orderPick.setSkuQuantity(skuQuantity);
        insert(orderPick);

        orderService.updateOrderPickState(orderIds,3,pickId);

        while (orderPickQuantityVos.size() == size){
            int i = oneSkuMultiQtyCreateWave(shipMethodIds,size,operatorId);
            if (i == 0){
                return i;
            }
        }
        return orderPickQuantityVos.size();
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