package com.upedge.oms.modules.pick.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.product.ListVariantsRequest;
import com.upedge.common.model.product.ProductVariantTo;
import com.upedge.common.model.ship.vo.ShippingMethodRedis;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.oms.modules.order.dto.AppOrderListDto;
import com.upedge.oms.modules.order.entity.Order;
import com.upedge.oms.modules.order.entity.OrderItem;
import com.upedge.oms.modules.order.request.AppOrderListRequest;
import com.upedge.oms.modules.order.service.OrderItemService;
import com.upedge.oms.modules.order.service.OrderService;
import com.upedge.oms.modules.pick.dao.OrderPickDao;
import com.upedge.oms.modules.pick.entity.OrderPick;
import com.upedge.oms.modules.pick.request.OrderPickCreateRequest;
import com.upedge.oms.modules.pick.request.OrderPickPreviewListRequest;
import com.upedge.oms.modules.pick.request.OrderPickWaveReleaseRequest;
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

    @Autowired
    PmsFeignClient pmsFeignClient;



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
    public BaseResponse releaseOrderWave(OrderPickWaveReleaseRequest request, Session session) {
        Integer waveNo = request.getWaveNo();
        OrderPick orderPick = orderPickDao.selectByWaveNo(waveNo);
        if (orderPick == null){
            return BaseResponse.failed("拣货信息不存在");
        }
        if (orderPick.getPickState() == -1){
            return BaseResponse.success();
        }
        String message = "success";
        boolean hasExStockOrder = false;
        List<Order> orders = orderService.selectByWaveNo(waveNo);
        if (ListUtils.isEmpty(orders)){
            for (Order order : orders) {
                if (order.getPackState() == 1 || order.getShipState() == 1){
                    hasExStockOrder = true;
                    message = "波次包含已出库的订单，波次保留，其他订单已释放";
                    break;
                }
            }
        }
        if (hasExStockOrder){
            orderPickDao.updatePickWaveRelease(waveNo);
        }
        orderService.updateOrderWaveRelease(waveNo);
        return BaseResponse.success(message);
    }

    @Override
    public List<OrderPickInfoVo> selectOrderPickInfo(Integer waveNo) {
        return orderPickDao.selectOrderPickInfo(waveNo);
    }

    @Override
    public OrderPickWaveInfoVo wavePickInfo(Integer waveNo) {
        OrderPick orderPick = orderPickDao.selectByWaveNo(waveNo);
        if (null == orderPick){
            return null;
        }
        OrderPickWaveInfoVo orderPickWaveInfoVo = new OrderPickWaveInfoVo();
        BeanUtils.copyProperties(orderPick,orderPickWaveInfoVo);

        AppOrderListRequest request = new AppOrderListRequest();
        AppOrderListDto appOrderListDto = new AppOrderListDto();
        appOrderListDto.setWaveNo(waveNo);
        request.setT(appOrderListDto);
        request.setPageSize(-1);

        List<OrderPickInfoVo> orderPickInfoVos = orderPickDao.selectOrderPickInfo(waveNo);

//        List<AppOrderVo> appOrderVos = orderService.selectAppOrderList(request);
        orderPickWaveInfoVo.setOrderPickInfoVos(orderPickInfoVos);

        List<OrderTwicePickVo.VariantOrderId> variantOrderIds = new ArrayList<>();

        Map<String,Set<Long>> barcodeOrderIds = new HashMap<>();
        orderPickInfoVos.forEach( orderPickInfoVo -> {
            List<OrderItemPickInfoVo> itemPickInfoVos = orderPickInfoVo.getOrderItemPickInfoVos();
            itemPickInfoVos.forEach(orderItemPickInfoVo -> {
                if (!barcodeOrderIds.containsKey(orderItemPickInfoVo.getBarcode())){
                    barcodeOrderIds.put(orderItemPickInfoVo.getBarcode(),new HashSet<>());
                }
                barcodeOrderIds.get(orderItemPickInfoVo.getBarcode()).add(orderPickInfoVo.getOrderId());
            });
        });
        barcodeOrderIds.forEach((barcode,orderIds) -> {
            OrderTwicePickVo.VariantOrderId variantOrderId = new OrderTwicePickVo.VariantOrderId();
            variantOrderId.setBarcode(barcode);
            variantOrderId.setOrderIds(orderIds);
            variantOrderIds.add(variantOrderId);
        });
        orderPickWaveInfoVo.setVariantOrderIds(variantOrderIds);
        return orderPickWaveInfoVo;
    }

    @Override
    public BaseResponse printPickInfo(Integer waveNo, Session session) {

        OrderPick orderPick = orderPickDao.selectByWaveNo(waveNo);

        List<OrderPickInfoVo> orderPickInfoVoList = orderPickDao.selectOrderPickInfo(waveNo);

        if (ListUtils.isEmpty(orderPickInfoVoList)){
            return BaseResponse.failed();
        }

        Integer quantity = 0;
        List<Long> variantIds = new ArrayList<>();
        Long shipMethodId = null;
        Map<Long,OrderItemPickInfoVo> map = new HashMap<>();
        for (OrderPickInfoVo orderPickInfoVo : orderPickInfoVoList) {
            if (shipMethodId == null){
                shipMethodId = orderPickInfoVo.getShipMethodId();
            }
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

        ListVariantsRequest listVariantsRequest = new ListVariantsRequest();
        listVariantsRequest.setVariantIds(variantIds);
        BaseResponse response = pmsFeignClient.listVariantByIds(listVariantsRequest);
        List<LinkedHashMap> variantDetailList = (List<LinkedHashMap>) response.getData();
        Map<Long,ProductVariantTo> variantToMap = new HashMap<>();
        variantDetailList.forEach(v -> {
            ProductVariantTo variantDetail = JSON.parseObject(JSON.toJSONString(v), ProductVariantTo.class);
            variantToMap.put(variantDetail.getId(),variantDetail);
        });

        List<OrderItemPickInfoVo> orderItemPickInfoVos = new ArrayList<>();
        map.forEach((variantId,item)-> {
            ProductVariantTo productVariantTo = variantToMap.get(item.getVariantId());
            if (productVariantTo != null){
                item.setVariantName(productVariantTo.getCnName());
            }
            orderItemPickInfoVos.add(item);
        });

        ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD,shipMethodId.toString());

        OrderPrintVo orderPrintVo = new OrderPrintVo();
        orderPrintVo.setWaveNo(orderPick.getWaveNo());
        orderPrintVo.setShipMethodName(shippingMethodRedis.getDesc());
        orderPrintVo.setOrderItemPickInfoVos(orderItemPickInfoVos);
        orderPrintVo.setSkuQuantity(quantity);
        orderPrintVo.setSkuType(map.size());
        orderPrintVo.setPackageCount(orderPickInfoVoList.size());
        return BaseResponse.success(orderPrintVo);
    }

    @Transactional
    @Override
    public BaseResponse twicePickSubmit(TwicePickSubmitRequest request, Session session) {
        Integer waveNo = request.getWaveNo();
        List<OrderPickInfoVo> orderPickInfoVos = request.getOrderPickInfoVos();

        Map<Long,OrderItemPickInfoVo> map = new HashMap<>();
        List<OrderPickInfoVo> orderPickInfoVoList = orderPickDao.selectOrderPickInfo(waveNo);
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
                if (pickedQuantity != orderItemPickInfoVo.getQuantity()){
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
            orderPick.setWaveNo(waveNo);
            orderPick.setUpdateTime(new Date());
            updateByPrimaryKeySelective(orderPick);
        }
        orderItemService.batchUpdatePickedQuantity(updatePickedQuantity);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse twicePickInfo(Integer waveNo) {

        OrderPick orderPick = orderPickDao.selectByWaveNo(waveNo);
        if (null == orderPick
                || orderPick.getPickType() != 2
        || orderPick.getPickState() < OrderPick.TO_BE_PICKED
        || orderPick.getPickState() > OrderPick.TWICE_PICK){
            return BaseResponse.failed();
        }

        List<OrderPickInfoVo> orderPickInfoVos = orderPickDao.selectOrderPickInfo(waveNo);

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
        orderPick.setWaveNo(waveNo);
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
            orderPickPreviewVo.setShipMethodName(shippingMethodRedis.getName());
            orderPickPreviewVo.setShipMethodDesc(shippingMethodRedis.getDesc());
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
        Integer pickType = request.getPickType();
        List<Long> orderIds = request.getOrderIds();
        if (ListUtils.isNotEmpty(orderIds)){
            if (pickType == 2 && orderIds.size()  > 12){
                return BaseResponse.failed("超过多品多件波次订单数量限制");
            }
            BaseResponse response = createWaveByOrderIds(orderIds,session.getId());
            RedisUtil.unLock(redisTemplate,key);
            return response;
        }
        List<Long> shipMethodIds = request.getShipMethodIds();
        String companyName = "";
        for (int i = 0; i < shipMethodIds.size(); i++) {
            Long shipMethodId = shipMethodIds.get(i);
            ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD,shipMethodId.toString());
            if (i == 0){
                companyName = shippingMethodRedis.getTrackingCompany();
            }else if (!companyName.equals(shippingMethodRedis.getTrackingCompany())){
                RedisUtil.unLock(redisTemplate,key);
                return BaseResponse.failed("不同物流公司的订单无法生成到同一波次");
            }
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
                RedisUtil.unLock(redisTemplate,key);
                return BaseResponse.failed();
        }
        RedisUtil.unLock(redisTemplate,key);
        return BaseResponse.success();
    }

    public BaseResponse createWaveByOrderIds(List<Long> orderIds,Long operatorId){
        List<Order> orders = orderService.selectByIds( orderIds);
        if (ListUtils.isEmpty(orders)){
            return BaseResponse.failed();
        }
        orderIds = new ArrayList<>();
        Integer pickType = null;
        String shipCompany = null;
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getPayState() != 1
            || order.getRefundState() != 0
            || order.getShipState() != 0
            || order.getPackState() != 1
            || order.getPickState() != 0
            || order.getWaveNo() != null){
                return BaseResponse.failed("包含不符合条件的订单");
            }
            ShippingMethodRedis shippingMethodRedis = (ShippingMethodRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_METHOD,order.getShipMethodId().toString());
            if (i == 0){
                pickType = order.getPickType();
                shipCompany = shippingMethodRedis.getTrackingCompany();
            }else {
                if (pickType != order.getPickType()
                || !shipCompany.equals(shippingMethodRedis.getTrackingCompany())){
                    continue;
                }
            }
            orderIds.add(order.getId());
        }
        int skuQuantity = 0;
        Set<Long>variantIds = new HashSet<>();
        List<OrderItem> orderItems = orderItemService.selectByOrderIds(orderIds);
        for (OrderItem orderItem : orderItems) {
            skuQuantity += orderItem.getQuantity();
            variantIds.add(orderItem.getAdminVariantId());
        }

        if (ListUtils.isEmpty(orderIds)){
            return BaseResponse.failed("未创建包裹的订单不能生成波次，同一波次的订单需要属于同一家运输公司，退款、已生成波次或已发货的订单不能再进行此操作");
        }

        Integer waveNo = getWaveNo();
        OrderPick orderPick = new OrderPick();
        orderPick.setPickType(pickType);
        orderPick.setId(IdGenerate.nextId());
        orderPick.setWaveNo(waveNo);
        orderPick.setOperatorId(operatorId);
        orderPick.setCreateTime(new Date());
        orderPick.setUpdateTime(new Date());
        orderPick.setSkuType(variantIds.size());
        if (pickType == 2){
            orderPick.setPickState(OrderPick.TO_BE_PICKED);
        }else {
            orderPick.setPickState(OrderPick.TO_BE_PACKED);
        }
        orderPick.setSkuQuantity(skuQuantity);
        insert(orderPick);

        orderService.updateOrderPickState(orderIds,1,waveNo);
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

        Integer waveNo = getWaveNo();
        OrderPick orderPick = new OrderPick();
        orderPick.setPickType(2);
        orderPick.setId(IdGenerate.nextId());
        orderPick.setWaveNo(waveNo);
        orderPick.setPickState(OrderPick.TO_BE_PICKED);
        orderPick.setOperatorId(operatorId);
        orderPick.setCreateTime(new Date());
        orderPick.setUpdateTime(new Date());
        orderPick.setSkuType(variantIds.size());
        orderPick.setSkuQuantity(size);
        insert(orderPick);

        orderService.updateOrderPickState(orderIds,1,waveNo);

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

        Integer waveNo = getWaveNo();
        OrderPick orderPick = new OrderPick();
        orderPick.setPickType(0);
        orderPick.setId(IdGenerate.nextId());
        orderPick.setWaveNo(waveNo);
        orderPick.setOperatorId(operatorId);
        orderPick.setCreateTime(new Date());
        orderPick.setUpdateTime(new Date());
        orderPick.setSkuType(1);
        orderPick.setPickState(OrderPick.TO_BE_PACKED);
        orderPick.setSkuQuantity(size);
        insert(orderPick);

        orderService.updateOrderPickState(orderIds,1,waveNo);

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

        Integer waveNo = getWaveNo();
        OrderPick orderPick = new OrderPick();
        orderPick.setPickType(1);
        orderPick.setId(IdGenerate.nextId());
        orderPick.setWaveNo(waveNo);
        orderPick.setOperatorId(operatorId);
        orderPick.setCreateTime(new Date());
        orderPick.setUpdateTime(new Date());
        orderPick.setSkuType(1);
        orderPick.setPickState(OrderPick.TO_BE_PACKED);
        orderPick.setSkuQuantity(skuQuantity);
        insert(orderPick);

        orderService.updateOrderPickState(orderIds,1,waveNo);

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


    private Integer getWaveNo(){
        String lockKey = "order:pick:wave:no:lock";

        boolean b = RedisUtil.lock(redisTemplate,lockKey,5L,10L);
        if (!b){
            return null;
        }
        String key = "order:pick:wave:no";
        Integer no = (Integer) redisTemplate.opsForValue().get(key);
        if(null == no){
            no = orderPickDao.selectMaxWaveNo();
            if (null == no){
                no = 10001;
            }
        }else {
            no += 1;
        }
        redisTemplate.opsForValue().set(key,no);
        RedisUtil.unLock(redisTemplate,lockKey);
        return no;
    }

}