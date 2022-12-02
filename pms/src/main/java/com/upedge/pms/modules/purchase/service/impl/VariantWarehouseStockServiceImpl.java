package com.upedge.pms.modules.purchase.service.impl;

import com.alibaba.fastjson.JSON;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.constant.key.RocketMqConfig;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.oms.order.ItemQuantityVo;
import com.upedge.common.model.oms.order.OrderItemQuantityVo;
import com.upedge.common.model.oms.order.OrderStockClearRequest;
import com.upedge.common.model.order.request.OrderStockStateUpdateRequest;
import com.upedge.common.model.pms.vo.VariantPreSaleQuantity;
import com.upedge.common.model.pms.vo.VariantWarehouseStockModel;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.purchase.dao.VariantWarehouseStockDao;
import com.upedge.pms.modules.purchase.entity.PurchasePlan;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStockRecord;
import com.upedge.pms.modules.purchase.request.*;
import com.upedge.pms.modules.purchase.service.VariantStockExImRecordService;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockRecordService;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockService;
import com.upedge.pms.modules.purchase.vo.VariantWarehouseStockVo;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Slf4j
@Service
public class VariantWarehouseStockServiceImpl implements VariantWarehouseStockService {

    @Autowired
    private VariantWarehouseStockDao variantWarehouseStockDao;

    @Autowired
    private VariantWarehouseStockRecordService variantWarehouseStockRecordService;

    @Autowired
    VariantStockExImRecordService variantStockExImRecordService;

    @Autowired
    ProductVariantService productVariantService;
    
    @Autowired
    OmsFeignClient omsFeignClient;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    DefaultMQProducer defaultMQProducer;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long variantId) {
        VariantWarehouseStock record = new VariantWarehouseStock();
        record.setVariantId(variantId);
        return variantWarehouseStockDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(VariantWarehouseStock record) {
        return variantWarehouseStockDao.insert(record);
    }

    @Override
    public int insertByBatch(List<VariantWarehouseStock> records) {
        if(ListUtils.isEmpty(records)){
            return 0;
        }
        return variantWarehouseStockDao.insertByBatch(records);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(VariantWarehouseStock record) {
        return variantWarehouseStockDao.insert(record);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int orderCancelShip(OrderItemQuantityVo orderItemQuantityVo) throws Exception {
        List<ItemQuantityVo> itemQuantityVos = orderItemQuantityVo.getItemQuantityVos();
        for (ItemQuantityVo itemQuantityVo : itemQuantityVos) {
            Integer lockedQuantity = itemQuantityVo.getLockedQuantity();
            if (lockedQuantity > 0){
                int i = variantWarehouseStockDao.restoreStockByLockStock(itemQuantityVo.getVariantId(), orderItemQuantityVo.getWarehouseCode(),lockedQuantity);
                if (i == 0){
                    throw new Exception("锁定库存异常");
                }
            }
        }
        return 1;
    }

    @Transactional
    @Override
    public BaseResponse deleteVariantStock(VariantWarehouseStockDeleteRequest request,Session session) {
        VariantWarehouseStock variantWarehouseStock = selectByPrimaryKey(request.getVariantId(), request.getWarehouseCode());
        if (null == variantWarehouseStock || variantWarehouseStock.getStockState() == 0){
            return BaseResponse.success();
        }
//        if (variantWarehouseStock.getPurchaseStock() > 0
//        || variantWarehouseStock.getLockStock() > 0){
//            return BaseResponse.failed("库存采购数量和锁定数量为0时才可以删除");
//        }
        VariantStockExImRecordUpdateRequest variantStockExImRecordUpdateRequest = new VariantStockExImRecordUpdateRequest();
        variantStockExImRecordUpdateRequest.setWarehouseCode(variantWarehouseStock.getWarehouseCode());
        variantStockExImRecordUpdateRequest.setVariantSku(variantWarehouseStock.getVariantSku());
        variantStockExImRecordUpdateRequest.setQuantity(variantWarehouseStock.getAvailableStock());
        variantStockExImRecordUpdateRequest.setProcessType(5);
        variantStockExImRecordUpdateRequest.setRelateId(System.currentTimeMillis());
        variantStockEx(variantStockExImRecordUpdateRequest,session);

        variantWarehouseStockDao.markAsDeleted(request);

        OrderStockClearRequest orderStockClearRequest = new OrderStockClearRequest();
        orderStockClearRequest.setVariantId(request.getVariantId());
        orderStockClearRequest.setWarehouseCode(request.getWarehouseCode());
        omsFeignClient.itemStockClear(orderStockClearRequest);

        redisTemplate.opsForHash().delete(RedisKey.HASH_VARIANT_WAREHOUSE_STOCK + request.getWarehouseCode(),request.getVariantId().toString());
        return BaseResponse.success();
    }

    @Override
    public int restoreStockByLockStock(Long variantId, String warehouseCode, Integer changeQuantity) {
        if (changeQuantity != null){
            return variantWarehouseStockDao.restoreStockByLockStock(variantId, warehouseCode, changeQuantity);
        }
        return 0;
    }

    @Override
    public BaseResponse variantStockList(VariantStockListRequest request) {
        List<VariantWarehouseStockVo> variantWarehouseStockVos = variantWarehouseStockDao.selectVariantStocks(request);
        List<Long> variantIds = new ArrayList<>();
        variantWarehouseStockVos.forEach(variantWarehouseStockVo -> {
            variantIds.add(variantWarehouseStockVo.getVariantId());
        });
        List<VariantPreSaleQuantity> variantPreSaleQuantities = omsFeignClient.selectVariantPreSaleQuantity(variantIds);
        a:
        for (VariantWarehouseStockVo variantWarehouseStockVo : variantWarehouseStockVos) {
            for (VariantPreSaleQuantity variantPreSaleQuantity : variantPreSaleQuantities) {
                if (variantPreSaleQuantity.getVariantId().equals(variantWarehouseStockVo.getVariantId())){
                    variantWarehouseStockVo.setPreSaleQuantity(variantPreSaleQuantity.getPreSaleQuantity());
                    variantPreSaleQuantities.remove(variantPreSaleQuantity);
                    continue a;
                }
            }
        }

        long count = variantWarehouseStockDao.countVariantStocks(request);
        request.setTotal(count);
        return BaseResponse.success(variantWarehouseStockVos,request);
    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public BaseResponse packageShipped(OrderItemQuantityVo orderItemQuantityVo) throws Exception {
        Set<Long> variantIds = new HashSet<>();
        List<ItemQuantityVo> itemQuantityVos = orderItemQuantityVo.getItemQuantityVos();
        for (ItemQuantityVo itemQuantityVo : itemQuantityVos) {
            VariantWarehouseStock variantWarehouseStock = variantWarehouseStockDao.selectByPrimaryKey(itemQuantityVo.getVariantId(), "CNHz");
            if (variantWarehouseStock == null || variantWarehouseStock.getLockStock() < itemQuantityVo.getQuantity()){
                throw new Exception("库存不足");
            }
            if (variantWarehouseStock.getStockState() == 0){
                return BaseResponse.failed("库存已被删除");
            }
            int i = variantWarehouseStockDao.reduceVariantLockStock(variantWarehouseStock.getVariantId(), "CNHZ",itemQuantityVo.getQuantity());
            if (i == 0){
                throw new Exception("库存不足");
            }
            Integer nowLockQuantity = variantWarehouseStock.getLockStock() - itemQuantityVo.getQuantity();
            VariantWarehouseStockRecord variantWarehouseStockRecord =
                    new VariantWarehouseStockRecord(variantWarehouseStock.getVariantId(),
                            "CNHZ",
                            itemQuantityVo.getQuantity(),
                            0,
                            variantWarehouseStock.getLockStock(),
                            nowLockQuantity,
                            itemQuantityVo.getItemId(),
                            new Date(),
                            "",
                            orderItemQuantityVo.getOperatorId());
            variantWarehouseStockRecordService.insert(variantWarehouseStockRecord);
            variantIds.add(itemQuantityVo.getVariantId());
        }
        return BaseResponse.success();
    }

    @Override
    public int updateVariantSafeStock(VariantSafeStockUpdateRequest request) {
        if (null == request.getSafeStock()
        || null == request.getVariantId()
        ||null == request.getWarehouseCode()){
            return 0;
        }
        return variantWarehouseStockDao.updateVariantSafeStock(request);
    }

    @Override
    public List<VariantWarehouseStock> selectByVariantIdsAndWarehouseCode(List<Long> variantIds, String warehouseCode) {
        return variantWarehouseStockDao.selectByVariantIdsAndWarehouseCode(variantIds, warehouseCode);
    }

    @Override
    public boolean updateVariantPurchaseStockByPlan(List<PurchasePlan> purchasePlans) {
        for (PurchasePlan purchasePlan : purchasePlans) {
            updateVariantPurchaseStockByPlan(purchasePlan);
        }
        return true;
    }

    boolean updateVariantPurchaseStockByPlan(PurchasePlan purchasePlan){
        VariantWarehouseStock variantWarehouseStock = variantWarehouseStockDao.selectByPrimaryKey(purchasePlan.getVariantId(), "CNHZ");
        if (variantWarehouseStock == null){
            variantWarehouseStock = new VariantWarehouseStock(purchasePlan.getVariantId(), "CNHZ",1, 0,0,purchasePlan.getQuantity(),"","");
            insert(variantWarehouseStock);
        }else {
            variantWarehouseStockDao.updateVariantPurchaseStock(purchasePlan.getVariantId(), "CNHZ",purchasePlan.getQuantity());
        }
        VariantWarehouseStockRecord variantWarehouseStockRecord =
                new VariantWarehouseStockRecord(purchasePlan.getVariantId(),
                        "CNHZ",
                        purchasePlan.getQuantity(),
                        VariantWarehouseStockRecord.PURCHASE_UPDATE,
                        variantWarehouseStock.getPurchaseStock(),
                        variantWarehouseStock.getPurchaseStock() + purchasePlan.getQuantity(),
                        purchasePlan.getId().longValue(),
                        new Date(),
                        null,
                        0L);
        variantWarehouseStockRecordService.insert(variantWarehouseStockRecord);
        return true;
    }
    @Override
    public boolean updateVariantPurchaseStockByPlan(Long variantId, String warehouseCode, Integer quantity){
        if (StringUtils.isBlank(warehouseCode)){
            warehouseCode = "CNHZ";
        }
        VariantWarehouseStock variantWarehouseStock = variantWarehouseStockDao.selectByPrimaryKey(variantId, warehouseCode);
        if (variantWarehouseStock == null){
            variantWarehouseStock = new VariantWarehouseStock(variantId, "CNHZ",1, 0,0,quantity,"","");
            insert(variantWarehouseStock);
        }else {
            variantWarehouseStockDao.updateVariantPurchaseStock(variantId, "CNHZ",quantity);
        }
        VariantWarehouseStockRecord variantWarehouseStockRecord =
                new VariantWarehouseStockRecord(variantId,
                        "CNHZ",
                        quantity,
                        VariantWarehouseStockRecord.PURCHASE_UPDATE,
                        variantWarehouseStock.getPurchaseStock(),
                        variantWarehouseStock.getPurchaseStock() + quantity,
                        null,
                        new Date(),
                        null,
                        0L);
        variantWarehouseStockRecordService.insert(variantWarehouseStockRecord);
        return true;
    }

//    @Transactional(rollbackFor = Exception.class)
//    @Override
//    public boolean orderCheckStock(OrderItemQuantityVo orderItemQuantityVo) throws Exception {
//        List<String> keys = new ArrayList<>();
//        List<ItemQuantityVo> itemQuantityVos = orderItemQuantityVo.getItemQuantityVos();
//        String warehouseCode = orderItemQuantityVo.getWarehouseCode();
//        Set<Long> variantIds = new HashSet<>();
//        //所有对应发货仓库的产品先加锁
//        for (ItemQuantityVo itemQuantityVo : itemQuantityVos) {
//            String key = RedisKey.KEY_VARIANT_WAREHOUSE_STOCK_LOCK+warehouseCode+":"+itemQuantityVo.getVariantId();
//            if (keys.contains(key)){
//                continue;
//            }
//            boolean b = RedisUtil.lock(redisTemplate,key,10L,10*1000L);
//            if (!b){
//                if (ListUtils.isNotEmpty(keys)){
//                    for (String s : keys) {
//                        RedisUtil.unLock(redisTemplate,s);
//                    }
//                    return false;
//                }
//            }
//            keys.add(key);
//            variantIds.add(itemQuantityVo.getVariantId());
//        }
//        Map<Long,VariantWarehouseStock> variantWarehouseStockModelMap = new HashMap<>();
//        Map<Long,Integer> variantChangeQuantity = new HashMap<>();
//        List<VariantWarehouseStockRecord> records = new ArrayList<>();
////        List<VariantWarehouseStock> variantWarehouseStocks = variantWarehouseStockDao.selectByVariantIdsAndWarehouseCode(variantIds,warehouseCode);
//        //判断订单下所有产品是否都有货
//        for (ItemQuantityVo itemQuantityVo : itemQuantityVos) {
//            Long variantId = itemQuantityVo.getVariantId();
//            //先从map里拿库存信息，没有就从redis拿
//            VariantWarehouseStock variantWarehouseStock = variantWarehouseStockModelMap.get(variantId);
//            if (null == variantWarehouseStock){
//                variantWarehouseStock = variantWarehouseStockDao.selectByPrimaryKey(variantId,warehouseCode);
//            }
//            if (null == variantWarehouseStock){
//                return true;
//            }
//
//            if (itemQuantityVo.getVariantId().equals(variantId)){
//                Integer changeQuantity = itemQuantityVo.getQuantity();
//                //安全数量小于变动库存，返回
//                if (changeQuantity > variantWarehouseStock.getAvailableStock()){
//                    return true;
//                }
//                Integer nowStock = variantWarehouseStock.getAvailableStock() - changeQuantity;
//                //保存库存锁定记录
//                VariantWarehouseStockRecord record = new VariantWarehouseStockRecord(variantId, warehouseCode, changeQuantity, VariantWarehouseStockRecord.STOCK_LOCK, variantWarehouseStock.getAvailableStock(), nowStock, itemQuantityVo.getItemId(), new Date(), "",0L);
//                records.add(record);
//                //更新对象最新安全库存
//                variantWarehouseStock.setAvailableStock(nowStock);
//                variantWarehouseStockModelMap.put(variantId,variantWarehouseStock);
//                //变体变动的库存
//                if (variantChangeQuantity.containsKey(variantId)){
//                    changeQuantity = changeQuantity + variantChangeQuantity.get(variantId);
//                }
//                variantChangeQuantity.put(variantId,changeQuantity);
//            }
//        }
//        //挨个修改锁定库存数量，修改失败则回滚
//        for (Map.Entry<Long,Integer> map:variantChangeQuantity.entrySet()){
//            int i = variantWarehouseStockDao.updateVariantWarehouseAvailableStock(map.getKey(), warehouseCode,map.getValue());
//            if (i == 0){
//                throw new Exception("库存不足");
//            }
//        }
//        OrderStockStateUpdateRequest orderStockStateUpdateRequest = new OrderStockStateUpdateRequest();
//        orderStockStateUpdateRequest.setStockState(1);
//        orderStockStateUpdateRequest.setOrderId(orderItemQuantityVo.getOrderId());
//        int i = omsFeignClient.updateStockState(orderStockStateUpdateRequest);
//        if (i != 1){
//            throw new Exception("订单异常");
//        }
//        variantWarehouseStockRecordService.insertByBatch(records);
//
//        sendRefreshVariantStockMessage(variantIds,warehouseCode);
//        for (String key : keys) {
//            RedisUtil.unLock(redisTemplate,key);
//        }
//        //修改订单缺货状态
//        return true;
//    }

    @GlobalTransactional(rollbackFor = Exception.class)
    @Override
    public boolean orderCheckStock(OrderItemQuantityVo orderItemQuantityVo) throws Exception {
        List<String> keys = new ArrayList<>();
        List<ItemQuantityVo> itemQuantityVos = orderItemQuantityVo.getItemQuantityVos();
        String warehouseCode = orderItemQuantityVo.getWarehouseCode();
//        Set<Long> variantIds = new HashSet<>();
        //所有对应发货仓库的产品先加锁
//        for (ItemQuantityVo itemQuantityVo : itemQuantityVos) {
//            String key = RedisKey.KEY_VARIANT_WAREHOUSE_STOCK_LOCK+warehouseCode+":"+itemQuantityVo.getVariantId();
//            if (keys.contains(key)){
//                continue;
//            }
//            boolean b = RedisUtil.lock(redisTemplate,key,10L,10*1000L);
//            if (!b){
//                if (ListUtils.isNotEmpty(keys)){
//                    for (String s : keys) {
//                        RedisUtil.unLock(redisTemplate,s);
//                    }
//                    return false;
//                }
//            }
//            keys.add(key);
//            variantIds.add(itemQuantityVo.getVariantId());
//        }

        orderItemQuantityVo.setStockState(1);
        Map<Long,VariantWarehouseStock> variantWarehouseStockModelMap = new HashMap<>();
        Map<Long,Integer> variantChangeQuantity = new HashMap<>();
        List<VariantWarehouseStockRecord> records = new ArrayList<>();
//        List<VariantWarehouseStock> variantWarehouseStocks = variantWarehouseStockDao.selectByVariantIdsAndWarehouseCode(variantIds,warehouseCode);
        //判断订单下所有产品是否都有货
        List<ItemQuantityVo> changeItems = new ArrayList<>();
        for (ItemQuantityVo itemQuantityVo : itemQuantityVos) {
            Integer changeQuantity = itemQuantityVo.getQuantity() - itemQuantityVo.getLockedQuantity();
            if (changeQuantity == 0){
                itemQuantityVo.setLockQuantity(0);
                continue;
            }
            Long variantId = itemQuantityVo.getVariantId();
            //先从map里拿库存信息，没有就从redis拿

            VariantWarehouseStock variantWarehouseStock = variantWarehouseStockModelMap.get(variantId);
            if (null == variantWarehouseStock){
                variantWarehouseStock = selectByPrimaryKey(variantId,warehouseCode);
            }
            if (null == variantWarehouseStock || variantWarehouseStock.getAvailableStock() == 0){
                itemQuantityVo.setLockQuantity(0);
                orderItemQuantityVo.setStockState(0);
                continue;
            }

            if (itemQuantityVo.getVariantId().equals(variantId)){
                //剩余库存小于所需库存，订单标记为缺货，剩余库存分配给该产品
                if (changeQuantity > variantWarehouseStock.getAvailableStock()){
                    orderItemQuantityVo.setStockState(0);
                    changeQuantity = variantWarehouseStock.getAvailableStock();
                }
                itemQuantityVo.setLockQuantity(changeQuantity);
                Integer nowStock = variantWarehouseStock.getAvailableStock() - changeQuantity;
                //保存库存锁定记录
                VariantWarehouseStockRecord record = new VariantWarehouseStockRecord(variantId, warehouseCode, changeQuantity, VariantWarehouseStockRecord.STOCK_LOCK, variantWarehouseStock.getAvailableStock(), nowStock, itemQuantityVo.getItemId(), new Date(), "",0L);
                records.add(record);
                //更新对象最新安全库存
                variantWarehouseStock.setAvailableStock(nowStock);
                variantWarehouseStockModelMap.put(variantId,variantWarehouseStock);
                //变体变动的库存
                if (variantChangeQuantity.containsKey(variantId)){
                    changeQuantity += variantChangeQuantity.get(variantId);
                }
                variantChangeQuantity.put(variantId,changeQuantity);
                changeItems.add(itemQuantityVo);
            }
        }
        //挨个修改锁定库存数量，修改失败则回滚
        for (Map.Entry<Long,Integer> map:variantChangeQuantity.entrySet()){
            int i = variantWarehouseStockDao.updateVariantWarehouseAvailableStock(map.getKey(), warehouseCode,map.getValue());
            if (i == 0){
                VariantWarehouseStock variantWarehouseStock = selectByPrimaryKey(map.getKey(), warehouseCode);
                if (null != variantWarehouseStock){
                    VariantWarehouseStockModel variantWarehouseStockModel = new VariantWarehouseStockModel();
                    BeanUtils.copyProperties(variantWarehouseStock,variantWarehouseStockModel);
                    redisTemplate.opsForHash().put(RedisKey.HASH_VARIANT_WAREHOUSE_STOCK+warehouseCode,map.getKey().toString(),variantWarehouseStockModel);
                }
                throw new Exception("库存不足");
            }
        }
        if (ListUtils.isNotEmpty(changeItems)){
            OrderStockStateUpdateRequest orderStockStateUpdateRequest = new OrderStockStateUpdateRequest();
            orderStockStateUpdateRequest.setStockState(orderItemQuantityVo.getStockState());
            orderStockStateUpdateRequest.setOrderId(orderItemQuantityVo.getOrderId());
            orderStockStateUpdateRequest.setItemQuantityVos(itemQuantityVos);
            int i = omsFeignClient.updateStockState(orderStockStateUpdateRequest);
            if (i != 1){
                throw new Exception("订单异常");
            }
        }

        variantWarehouseStockRecordService.insertByBatch(records);
        variantWarehouseStockModelMap.forEach((variantId,model) ->{
            VariantWarehouseStockModel variantWarehouseStockModel = new VariantWarehouseStockModel();
            BeanUtils.copyProperties(model,variantWarehouseStockModel);
            redisTemplate.opsForHash().put(RedisKey.HASH_VARIANT_WAREHOUSE_STOCK+warehouseCode,variantId.toString(),variantWarehouseStockModel);
        });
//        for (String key : keys) {
//            RedisUtil.unLock(redisTemplate,key);
//        }
        //修改订单缺货状态
        return true;
    }

    @Override
    public BaseResponse variantWarehouseStockList(VariantWarehouseStockListRequest request) {
        List<VariantWarehouseStockVo> variantWarehouseStockVos = new ArrayList<>();
        List<VariantWarehouseStock> variantWarehouseStocks = select(request);
        if (ListUtils.isNotEmpty(variantWarehouseStocks)){
            List<Long> variantIds = new ArrayList<>();
            for (VariantWarehouseStock variantWarehouseStock : variantWarehouseStocks) {
                variantIds.add(variantWarehouseStock.getVariantId());
            }
            List<ProductVariant> productVariants = productVariantService.listVariantByIds(variantIds);
            List<VariantPreSaleQuantity> variantPreSaleQuantities = omsFeignClient.selectVariantPreSaleQuantity(variantIds);
            a:
            for (VariantWarehouseStock variantWarehouseStock : variantWarehouseStocks) {
                VariantWarehouseStockVo variantWarehouseStockVo = new VariantWarehouseStockVo();
                BeanUtils.copyProperties(variantWarehouseStock,variantWarehouseStockVo);
                variantWarehouseStockVos.add(variantWarehouseStockVo);
                for (ProductVariant productVariant : productVariants) {
                    if (productVariant.getId().equals(variantWarehouseStock.getVariantId())){
                        BeanUtils.copyProperties(productVariant,variantWarehouseStockVo);
                    }
                }
                if (ListUtils.isEmpty(variantPreSaleQuantities)){
                    continue a;
                }
                for (VariantPreSaleQuantity variantPreSaleQuantity : variantPreSaleQuantities) {
                    if (variantPreSaleQuantity.getVariantId().equals(variantWarehouseStock.getVariantId())){
                        variantWarehouseStockVo.setPreSaleQuantity(variantPreSaleQuantity.getPreSaleQuantity());
                        variantPreSaleQuantities.remove(variantPreSaleQuantity);
                        continue a;
                    }
                }
            }
        }
        Long total = count(request);
        request.setTotal(total);
        return BaseResponse.success(variantWarehouseStockVos,request);
    }

    @Transactional
    @Override
    public BaseResponse updateVariantStock(VariantStockUpdateRequest request, Session session) {
        Long variantId = request.getVariantId();
        String warehouseCode = request.getWarehouseCode();
        String key = "key:variantUpdateStock:"+ variantId + ":" + warehouseCode;
        boolean b = RedisUtil.lock(redisTemplate,key,10L,10 * 1000L);
        if (!b){
            return BaseResponse.failed();
        }
        Integer originalQuantity = 0;

        VariantWarehouseStockModel variantWarehouseStockModel = (VariantWarehouseStockModel) redisTemplate.opsForHash().get(RedisKey.HASH_VARIANT_WAREHOUSE_STOCK+warehouseCode,variantId.toString());
        VariantWarehouseStock variantWarehouseStock = selectByPrimaryKey(variantId,warehouseCode);
        if (null == variantWarehouseStock ){
            variantWarehouseStock = new VariantWarehouseStock(variantId,warehouseCode,1, request.getStock(), 0,0,"","");
            insert(variantWarehouseStock);
            variantWarehouseStockModel = new VariantWarehouseStockModel();
            BeanUtils.copyProperties(variantWarehouseStock,variantWarehouseStockModel);
        }else {
            if (variantWarehouseStockModel == null){
                variantWarehouseStockModel = new VariantWarehouseStockModel();
            }
            BeanUtils.copyProperties(variantWarehouseStock,variantWarehouseStockModel);
            if (variantWarehouseStock.getStockState() == 0){
                return BaseResponse.failed("库存已被删除");
            }
            originalQuantity = variantWarehouseStock.getAvailableStock();
            variantWarehouseStock = new VariantWarehouseStock();
            variantWarehouseStock.setAvailableStock(request.getStock());
            variantWarehouseStock.setVariantId(variantId);
            variantWarehouseStock.setWarehouseCode(warehouseCode);
            variantWarehouseStock.setUpdateTime(new Date());
            if (StringUtils.isNotBlank(request.getRemark())){
                variantWarehouseStock.setRemark(request.getRemark());
            }
            updateByPrimaryKeySelective(variantWarehouseStock);

            variantWarehouseStockModel.setAvailableStock(request.getStock());
        }

        VariantWarehouseStockRecord variantWarehouseStockRecord = new VariantWarehouseStockRecord(variantId,
                        warehouseCode,
                        request.getStock(),
                        VariantWarehouseStockRecord.CUSTOM_UPDATE,
                        originalQuantity,
                        request.getStock(),
                        IdGenerate.nextId(),
                        new Date(),
                        null,
                        session.getId());
        variantWarehouseStockRecordService.insert(variantWarehouseStockRecord);

        redisTemplate.opsForHash().put(RedisKey.HASH_VARIANT_WAREHOUSE_STOCK + warehouseCode,variantId.toString(),variantWarehouseStockModel);

//        if (request.getStock() > 0 && variantWarehouseStockRecord.getOriginalStock() != variantWarehouseStockRecord.getNowStock()){
//            OrderItemQuantityDto orderItemQuantityDto = new OrderItemQuantityDto();
//            orderItemQuantityDto.setVariantId(variantWarehouseStock.getVariantId());
//            omsFeignClient.checkStock(orderItemQuantityDto);
//        }
        RedisUtil.unLock(redisTemplate,key);
        return BaseResponse.success();
    }

    //出库
    @Transactional
    @Override
    public BaseResponse variantStockEx(VariantStockExImRecordUpdateRequest request, Session session) {

        ProductVariant productVariant = productVariantService.selectBySku(request.getVariantSku());
        if (null == productVariant){
            return BaseResponse.failed("变体不存在");
        }
        String key = "key:variantStockEx:"+ productVariant.getId();
        boolean b = RedisUtil.lock(redisTemplate,key,10L,20 * 1000L);
        if (!b){
            return BaseResponse.failed();
        }

        VariantWarehouseStock variantWarehouseStock = variantWarehouseStockDao.selectByPrimaryKey(productVariant.getId(), request.getWarehouseCode());
        if (variantWarehouseStock.getStockState() == 0){
            return BaseResponse.failed("库存已被删除");
        }
        if (variantWarehouseStock.getAvailableStock() < request.getQuantity()){
            RedisUtil.unLock(redisTemplate,key);
            return BaseResponse.failed("仓库数量不足");
        }

        variantWarehouseStockDao.updateVariantStockEx(productVariant.getId(), request.getWarehouseCode(), request.getQuantity());

        VariantWarehouseStockRecord variantWarehouseStockRecord =
                new VariantWarehouseStockRecord(productVariant.getId(),
                        request.getWarehouseCode(),
                        request.getQuantity(),
                        request.getProcessType(),
                        variantWarehouseStock.getAvailableStock(),
                        variantWarehouseStock.getAvailableStock() - request.getQuantity(),
                        request.getRelateId(),
                        new Date(),
                        "",
                        session.getId());
        variantWarehouseStockRecordService.insert(variantWarehouseStockRecord);

        variantWarehouseStock.setAvailableStock(variantWarehouseStockRecord.getNowStock());

        VariantWarehouseStockModel variantWarehouseStockModel = new VariantWarehouseStockModel();
        BeanUtils.copyProperties(variantWarehouseStock,variantWarehouseStockModel);
        redisTemplate.opsForHash().put(RedisKey.HASH_VARIANT_WAREHOUSE_STOCK+variantWarehouseStock.getWarehouseCode(),variantWarehouseStock.getVariantId().toString(),variantWarehouseStockModel);

        RedisUtil.unLock(redisTemplate,key);
        return BaseResponse.success();
    }

    //入库
    @Transactional
    @Override
    public BaseResponse variantStockIm(VariantStockExImRecordUpdateRequest request, Session session) {
        String sku = request.getVariantSku();
        String barcode = request.getBarcode();
        ProductVariant productVariant = null;
        if (StringUtils.isNotBlank(sku)){
            productVariant = productVariantService.selectBySku(request.getVariantSku());
        }
        if (StringUtils.isNotBlank(barcode)){
            productVariant = productVariantService.selectByBarcode(barcode);
        }
        if (null == productVariant){
            return BaseResponse.failed("变体不存在");
        }
        String key = "key:variantStockIm:"+ productVariant.getId();
        boolean b = RedisUtil.lock(redisTemplate,key,10L,20 * 1000L);
        if (!b){
            return BaseResponse.failed();
        }
        int i = 0;
        VariantWarehouseStock variantWarehouseStock = variantWarehouseStockDao.selectByPrimaryKey(productVariant.getId(), request.getWarehouseCode());
        if (null == variantWarehouseStock){
            variantWarehouseStock = new VariantWarehouseStock(productVariant.getId(), request.getWarehouseCode(), 0, request.getQuantity(), 0,0,"","");
            i = insert(variantWarehouseStock);
        }else {
            i = variantWarehouseStockDao.updateVariantStockIm(productVariant.getId(), request.getWarehouseCode(), request.getQuantity(), request.getProcessType());
        }

        if (i == 0){
            RedisUtil.unLock(redisTemplate,key);
            return BaseResponse.failed();
        }

        VariantWarehouseStockRecord variantWarehouseStockRecord =
                new VariantWarehouseStockRecord(productVariant.getId(),
                        request.getWarehouseCode(),
                        request.getQuantity(),
                        request.getProcessType(),
                        variantWarehouseStock.getAvailableStock(),
                        variantWarehouseStock.getAvailableStock() + request.getQuantity(),
                        request.getRelateId(),
                        new Date(),
                        "",
                        session.getId());
        variantWarehouseStockRecordService.insert(variantWarehouseStockRecord);

        variantWarehouseStock.setAvailableStock(variantWarehouseStockRecord.getNowStock());

        VariantWarehouseStockModel variantWarehouseStockModel = new VariantWarehouseStockModel();
        BeanUtils.copyProperties(variantWarehouseStock,variantWarehouseStockModel);
        redisTemplate.opsForHash().put(RedisKey.HASH_VARIANT_WAREHOUSE_STOCK+variantWarehouseStock.getWarehouseCode(),variantWarehouseStock.getVariantId().toString(),variantWarehouseStockModel);

//        OrderItemQuantityDto orderItemQuantityDto = new OrderItemQuantityDto();
//        orderItemQuantityDto.setVariantId(productVariant.getId());
//        orderItemQuantityDto.setStockType(1);
//        omsFeignClient.checkStock(orderItemQuantityDto);

        RedisUtil.unLock(redisTemplate,key);
        return BaseResponse.success();
    }

    /**
     *
     */
    public VariantWarehouseStock selectByPrimaryKey(Long variantId,String warehouseCode){

        return variantWarehouseStockDao.selectByPrimaryKey(variantId, warehouseCode);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(VariantWarehouseStock record) {
        return variantWarehouseStockDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(VariantWarehouseStock record) {
        return variantWarehouseStockDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<VariantWarehouseStock> select(Page<VariantWarehouseStock> record){
        record.initFromNum();
        return variantWarehouseStockDao.select(record);
    }

    /**
    *
    */
    public long count(Page<VariantWarehouseStock> record){
        return variantWarehouseStockDao.count(record);
    }

    public void refreshRedisVariantStock(Long variantId,String warehouseCode){

    }

    void sendRefreshVariantStockMessage(Set<Long> variantIds,String warehouseCode){
        Message message = new Message(RocketMqConfig.TOPIC_VARIANT_WAREHOUSE_STOCK_REFRESH,warehouseCode, JSON.toJSONBytes(variantIds));
        try {
            defaultMQProducer.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}