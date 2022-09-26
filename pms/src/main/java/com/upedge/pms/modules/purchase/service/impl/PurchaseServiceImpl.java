package com.upedge.pms.modules.purchase.service.impl;

import com.alibaba.trade.param.AlibabaCreateOrderPreviewResultCargoModel;
import com.alibaba.trade.param.AlibabaCreateOrderPreviewResultModel;
import com.alibaba.trade.param.AlibabaTradeFastCargo;
import com.alibaba.trade.param.AlibabaTradeFastResult;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.order.dto.OrderItemPurchaseAdviceDto;
import com.upedge.common.model.order.vo.OrderItemPurchaseAdviceVo;
import com.upedge.common.model.pms.vo.PurchaseAdviceItemVo;
import com.upedge.common.model.pms.vo.PurchaseAdviceVo;
import com.upedge.common.model.product.AlibabaApiVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.purchase.entity.*;
import com.upedge.pms.modules.purchase.request.PurchaseAdviceRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderCreateRequest;
import com.upedge.pms.modules.purchase.service.*;
import com.upedge.pms.modules.purchase.vo.PurchaseOrderVo;
import com.upedge.thirdparty.ali1688.service.Ali1688Service;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    ProductVariantService productVariantService;

    @Autowired
    ProductPurchaseInfoService productPurchaseInfoService;

    @Autowired
    VariantWarehouseStockService variantWarehouseStockService;

    @Autowired
    PurchasePlanService purchasePlanService;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    PurchaseOrderItemService purchaseOrderItemService;

    @Autowired
    OmsFeignClient omsFeignClient;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;


    @Override
    public BaseResponse cancelPurchase(List<Long> variantIds, Session session) {
        redisTemplate.opsForList().leftPushAll(RedisKey.STRING_VARIANT_CANCEL_PURCHASE_LIST,variantIds);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse restorePurchase(List<Long> variantIds, Session session) {
        List<Long> cancelPurchaseVariantIds = redisTemplate.opsForList().range(RedisKey.STRING_VARIANT_CANCEL_PURCHASE_LIST,0,-1);
        if (ListUtils.isEmpty(cancelPurchaseVariantIds)){
            return BaseResponse.success();
        }
        cancelPurchaseVariantIds.removeAll(variantIds);
        redisTemplate.delete(RedisKey.STRING_VARIANT_CANCEL_PURCHASE_LIST);
        if (ListUtils.isEmpty(cancelPurchaseVariantIds)){
            return BaseResponse.success();
        }
        cancelPurchaseVariantIds = cancelPurchaseVariantIds.stream().distinct().collect(Collectors.toList());
        redisTemplate.opsForList().leftPushAll(RedisKey.STRING_VARIANT_CANCEL_PURCHASE_LIST,cancelPurchaseVariantIds);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse purchaseAdvice() {
        String warehouseCode = "CNHZ";
        redisTemplate.delete(RedisKey.HASH_PURCHASE_ADVICE_LIST);
        OrderItemPurchaseAdviceVo purchaseAdviceVo = omsFeignClient.purchaseItems();
        if (purchaseAdviceVo == null) {
            return BaseResponse.success(new ArrayList<>());
        }

        List<PurchaseAdviceItemVo> purchaseAdviceItemVos = purchaseAdviceVo.getPurchaseAdviceItemVos();
        if (ListUtils.isNotEmpty(purchaseAdviceItemVos)){
            List<Long> planingVariantIds = purchasePlanService.selectPlaningVariantIds();
            if (null == planingVariantIds){
                planingVariantIds = new ArrayList<>();
            }
            for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
                if (!planingVariantIds.contains(purchaseAdviceItemVo.getVariantId()))
                    redisTemplate.opsForHash().put(RedisKey.HASH_PURCHASE_ADVICE_LIST,purchaseAdviceItemVo.getVariantId().toString(),purchaseAdviceItemVo);
            }
        }

        List<Long> variantIds = new ArrayList<>();
        for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
            variantIds.add(purchaseAdviceItemVo.getVariantId());
        }




        List<Long> cancelPurchaseVariantIds = redisTemplate.opsForList().range(RedisKey.STRING_VARIANT_CANCEL_PURCHASE_LIST,0,-1);
        List<Long> shuntPurchaseVariantIds = redisTemplate.opsForList().range(RedisKey.STRING_VARIANT_SHUNT_PURCHASE_LIST,0,-1);
        variantIds.removeAll(cancelPurchaseVariantIds);
        variantIds.removeAll(shuntPurchaseVariantIds);

        List<PurchaseAdviceVo> adviceVos = getPurchaseAdvices(variantIds,warehouseCode,purchaseAdviceItemVos);
//        Map<String,List<PurchaseAdviceVo>> map = new HashMap<>();
//        CompletableFuture<Void> advice = CompletableFuture.runAsync(new Runnable() {
//            @Override
//            public void run() {
//
//                map.put("advice",adviceVos);
//            }
//        },threadPoolExecutor);
//
//        CompletableFuture<Void> cancel = CompletableFuture.runAsync(new Runnable() {
//            @Override
//            public void run() {
//                List<PurchaseAdviceVo> adviceVos = getPurchaseAdvices(cancelPurchaseVariantIds,warehouseCode,purchaseAdviceItemVos);
//                map.put("cancel",adviceVos);
//            }
//        },threadPoolExecutor);
//
//        try {
//            CompletableFuture.allOf(advice,cancel).get();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return BaseResponse.success(adviceVos);
    }

    @Override
    public BaseResponse purchaseAdviceCache(PurchaseAdviceRequest request) {
        Integer type = request.getType();
        List<Long> variantIds = new ArrayList<>();

        OrderItemPurchaseAdviceDto orderItemPurchaseAdviceDto = request;
        if (null == orderItemPurchaseAdviceDto){
            orderItemPurchaseAdviceDto = new OrderItemPurchaseAdviceDto();
        }
        String barcode = request.getBarcode();
        String variantSku = request.getVariantSku();
        ProductVariant productVariant = productVariantService.selectByBarcode(barcode);
        if (null == productVariant){
            productVariant = productVariantService.selectBySku(variantSku);
        }
        if (null != productVariant){
            variantIds.add(productVariant.getId());
        }else {
            variantIds = omsFeignClient.selectItemAdminVariantIds(orderItemPurchaseAdviceDto);
        }
        List<Long> planingVariantIds = purchasePlanService.selectPlaningVariantIds();
        variantIds.removeAll(planingVariantIds);
        if (ListUtils.isEmpty(variantIds)){
            return BaseResponse.success();
        }
        List<Long> cancelPurchaseVariantIds = redisTemplate.opsForList().range(RedisKey.STRING_VARIANT_CANCEL_PURCHASE_LIST,0,-1);
        List<Long> shuntPurchaseVariantIds = redisTemplate.opsForList().range(RedisKey.STRING_VARIANT_SHUNT_PURCHASE_LIST,0,-1);
        List<Long> removeIds = new ArrayList<>();
        switch (type){
            case 0://取消采购的产品
                if (ListUtils.isEmpty(cancelPurchaseVariantIds)){
                    variantIds.clear();
                    break;
                }
                for (Long variantId : variantIds) {
                    if (!cancelPurchaseVariantIds.contains(variantId)){
                        removeIds.add(variantId);
                    }
                }
                break;
            case 1://正常采购的产品
                variantIds.removeAll(cancelPurchaseVariantIds);
                variantIds.removeAll(shuntPurchaseVariantIds);
                break;
            case -1://搁置采购的产品
                if (ListUtils.isEmpty(shuntPurchaseVariantIds)){
                    variantIds.clear();
                    break;
                }
                for (Long variantId : variantIds) {
                    if (!shuntPurchaseVariantIds.contains(variantId)){
                        removeIds.add(variantId);
                    }
                }
                break;
        }
        variantIds.removeAll(removeIds);
        if (ListUtils.isEmpty(variantIds)){
            return BaseResponse.success();
        }
        List<PurchaseAdviceItemVo> purchaseAdviceItemVos = new ArrayList<>();
        variantIds.forEach(variantId -> {
            PurchaseAdviceItemVo purchaseAdviceItemVo = (PurchaseAdviceItemVo) redisTemplate.opsForHash().get(RedisKey.HASH_PURCHASE_ADVICE_LIST,variantId.toString());
            if (null != purchaseAdviceItemVo){
                purchaseAdviceItemVos.add(purchaseAdviceItemVo);
            }
        });
        List<PurchaseAdviceVo> adviceVos = getPurchaseAdvices(variantIds,"CNHZ",purchaseAdviceItemVos);
        return BaseResponse.success(adviceVos);
    }

    private List<PurchaseAdviceVo> getPurchaseAdvices(List<Long> variantIds,String warehouseCode,List<PurchaseAdviceItemVo> purchaseAdviceItemVos){
        if (ListUtils.isEmpty(variantIds) || ListUtils.isEmpty(purchaseAdviceItemVos)){
            return new ArrayList<>();
        }
        if (StringUtils.isBlank(warehouseCode)){
            warehouseCode = "CNHZ";
        }
        Set<String> purchaseSkus = new HashSet<>();
        //产品信息
        List<ProductVariant> productVariants = productVariantService.listVariantByIds(variantIds);
        //产品库存信息
        List<VariantWarehouseStock> variantWarehouseStocks = variantWarehouseStockService.selectByVariantIdsAndWarehouseCode(variantIds, warehouseCode);

        List<PurchaseAdviceItemVo> removeItems = new ArrayList<>();
        //产品信息，库存信息匹配
        a:
        for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
            Long variantId = purchaseAdviceItemVo.getVariantId();
            if (!variantIds.contains(variantId)){
                continue a;
            }
            b:
            for (VariantWarehouseStock variantWarehouseStock : variantWarehouseStocks) {
                if (variantWarehouseStock.getVariantId().equals(variantId)) {
                    purchaseAdviceItemVo.setSafeStock(variantWarehouseStock.getSafeStock());
                    purchaseAdviceItemVo.setPurchaseQuantity(variantWarehouseStock.getPurchaseStock());
                    purchaseAdviceItemVo.setAvailableStock(variantWarehouseStock.getAvailableStock());
                    purchaseAdviceItemVo.setLockQuantity(variantWarehouseStock.getLockStock());
                    variantWarehouseStocks.remove(variantWarehouseStock);

                    if (purchaseAdviceItemVo.getOrderQuantity() - purchaseAdviceItemVo.getPurchaseQuantity() - purchaseAdviceItemVo.getAvailableStock() < 1){
                        removeItems.add(purchaseAdviceItemVo);
                        continue a;
                    }

                    break b;
                }
            }
            for (ProductVariant productVariant : productVariants) {
                purchaseSkus.add(productVariant.getPurchaseSku());
                if (purchaseAdviceItemVo.getVariantId().equals(productVariant.getId())) {
                    BeanUtils.copyProperties(productVariant, purchaseAdviceItemVo);
                    productVariants.remove(productVariant);
                    continue a;
                }
            }
        }

        purchaseAdviceItemVos.removeAll(removeItems);

        //采购信息匹配
        List<ProductPurchaseInfo> productPurchaseInfos = productPurchaseInfoService.selectByPurchaseSkus(purchaseSkus);
        Map<String, PurchaseAdviceVo> purchaseAdviceVoMap = new HashMap<>();
        a:
        for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
            if (purchaseAdviceItemVo.getPurchaseSku() == null) {
                if (variantIds.contains(purchaseAdviceItemVo.getVariantId())){
                    String noSupplier = "缺少供应商信息";
                    if (!purchaseAdviceVoMap.containsKey(noSupplier)){
                        PurchaseAdviceVo purchaseAdviceVo = new PurchaseAdviceVo();
                        purchaseAdviceVo.setSupplierName(noSupplier);
                        purchaseAdviceVo.setWarehouseCode(warehouseCode);
                        purchaseAdviceVoMap.put(noSupplier,purchaseAdviceVo);
                    }
                    purchaseAdviceVoMap.get(noSupplier).getPurchaseAdviceItemVos().add(purchaseAdviceItemVo);
                }
                continue;
            }
            for (ProductPurchaseInfo productPurchaseInfo : productPurchaseInfos) {
                String supplierName = productPurchaseInfo.getSupplierName();
                String purchaseSku = productPurchaseInfo.getPurchaseSku();
                if (purchaseAdviceItemVo.getPurchaseSku().equals(purchaseSku)) {
                    PurchaseAdviceVo purchaseAdviceVo = purchaseAdviceVoMap.get(supplierName);
                    if (purchaseAdviceVo == null) {
                        purchaseAdviceVo = new PurchaseAdviceVo();
                        purchaseAdviceVo.setSupplierName(supplierName);
                        purchaseAdviceVo.setWarehouseCode(warehouseCode);
                    }
                    purchaseAdviceVo.getPurchaseAdviceItemVos().add(purchaseAdviceItemVo);
                    purchaseAdviceVoMap.put(supplierName, purchaseAdviceVo);
                    continue a;
                }
            }
        }
        if (MapUtils.isEmpty(purchaseAdviceVoMap)) {
            return new ArrayList<>();
        }
        List<PurchaseAdviceVo> purchaseAdviceVos = new ArrayList<>();
        for (Map.Entry<String, PurchaseAdviceVo> map : purchaseAdviceVoMap.entrySet()) {
            purchaseAdviceVos.add(map.getValue());
        }
        return purchaseAdviceVos;
    }

    @Override
    public BaseResponse previewPurchaseOrder(PurchaseOrderCreateRequest request, Session session) {
        List<PurchasePlan> purchasePlans = purchasePlanService.selectByIds(request.getIds());
        if (ListUtils.isEmpty(purchasePlans)) {
            return BaseResponse.success(new ArrayList<>());
        }

        Map<String, PurchasePlan> skuPurchasePlanMap = new HashMap<>();
        Set<String> purchaseSkus = new HashSet<>();
        for (PurchasePlan purchasePlan : purchasePlans) {
            purchaseSkus.add(purchasePlan.getPurchaseSku());
            skuPurchasePlanMap.put(purchasePlan.getSpecId(), purchasePlan);
        }
        List<ProductPurchaseInfo> productPurchaseInfos = productPurchaseInfoService.selectByPurchaseSkus(purchaseSkus);

        Map<String, List<AlibabaTradeFastCargo>> supplierCargosMap = new HashMap<>();

        for (PurchasePlan purchasePlan : purchasePlans) {
            if (!supplierCargosMap.containsKey(purchasePlan.getSupplierName())) {
                supplierCargosMap.put(purchasePlan.getSupplierName(), new ArrayList<AlibabaTradeFastCargo>());
            }
            for (ProductPurchaseInfo productPurchaseInfo : productPurchaseInfos) {
                if (productPurchaseInfo.getPurchaseSku().equals(purchasePlan.getPurchaseSku())) {
                    AlibabaTradeFastCargo alibabaTradeFastCargo = new AlibabaTradeFastCargo();
                    alibabaTradeFastCargo.setOfferId(Long.parseLong(productPurchaseInfo.getPurchaseLink()));
                    alibabaTradeFastCargo.setSpecId(productPurchaseInfo.getSpecId());
                    alibabaTradeFastCargo.setQuantity(purchasePlan.getQuantity().doubleValue());
//                    alibabaTradeFastCargos.add(alibabaTradeFastCargo);
                    supplierCargosMap.get(purchasePlan.getSupplierName()).add(alibabaTradeFastCargo);
                }
            }
        }

        AlibabaApiVo alibabaApiVo = (AlibabaApiVo) redisTemplate.opsForValue().get(RedisKey.STRING_ALI1688_API);
        List<PurchaseOrderVo> purchaseOrderVos = new ArrayList<>();
        for (Map.Entry<String, List<AlibabaTradeFastCargo>> map : supplierCargosMap.entrySet()) {
            try {
                List<AlibabaCreateOrderPreviewResultModel> previewResultModels = Ali1688Service.createOrderPreview(map.getValue(), alibabaApiVo);
                for (AlibabaCreateOrderPreviewResultModel previewResultModel : previewResultModels) {
                    PurchaseOrderVo purchaseOrderVo = new PurchaseOrderVo();
                    purchaseOrderVo.setShipPrice(new BigDecimal(previewResultModel.getSumCarriage() / 100));
                    purchaseOrderVo.setProductAmount(new BigDecimal(previewResultModel.getSumPaymentNoCarriage().doubleValue() / 100));
                    purchaseOrderVo.setAmount(new BigDecimal(previewResultModel.getSumPayment().doubleValue() / 100));
                    purchaseOrderVo.setSupplierName(map.getKey());

                    List<PurchaseOrderItem> purchaseItemVos = new ArrayList<>();
                    List<AlibabaCreateOrderPreviewResultCargoModel> resultCargoModels = Arrays.asList(previewResultModel.getCargoList());
                    for (AlibabaCreateOrderPreviewResultCargoModel resultCargoModel : resultCargoModels) {
                        PurchasePlan purchasePlan = skuPurchasePlanMap.get(resultCargoModel.getSpecId());
                        if (null == purchasePlan) {
                            continue;
                        }
                        BigDecimal price = new BigDecimal(resultCargoModel.getFinalUnitPrice());
                        PurchaseOrderItem purchaseItemVo = new PurchaseOrderItem();
                        BeanUtils.copyProperties(purchasePlan, purchaseItemVo);
                        purchaseItemVo.setPrice(price);
                        purchaseItemVos.add(purchaseItemVo);
                        purchaseOrderVo.setCargoPromotionList(Arrays.asList(resultCargoModel.getCargoPromotionList()));
                        purchasePlanService.updatePriceById(purchasePlan.getId(),price);
                    }
                    purchaseOrderVo.setPurchaseItemVos(purchaseItemVos);
                    purchaseOrderVos.add(purchaseOrderVo);
                }
            } catch (CustomerException e) {
                return BaseResponse.failed(e.getMessage());
            }
        }

        return BaseResponse.success(purchaseOrderVos);
    }


    @Transactional
    @Override
    public List<Long> createPurchaseOrder(PurchaseOrderCreateRequest request, Session session) throws CustomerException {
        String key = "key:createPurchaseOrder";
        boolean b = RedisUtil.lock(redisTemplate,key,10L,60 * 1000L);
        if (!b){
            throw new CustomerException("其他采购单正在生成中，请稍候。");
        }

        List<PurchasePlan> purchasePlans = purchasePlanService.selectByIds(request.getIds());
        if (ListUtils.isEmpty(purchasePlans)) {
            return new ArrayList<>();
        }

        Map<String, PurchasePlan> skuPurchasePlanMap = new HashMap<>();
        Set<String> purchaseSkus = new HashSet<>();
        for (PurchasePlan purchasePlan : purchasePlans) {
            purchaseSkus.add(purchasePlan.getPurchaseSku());
            skuPurchasePlanMap.put(purchasePlan.getSpecId(), purchasePlan);
        }
        List<ProductPurchaseInfo> productPurchaseInfos = productPurchaseInfoService.selectByPurchaseSkus(purchaseSkus);

        Map<String, List<Integer>> supplierPurchasePlans = new HashMap<>();
        Map<String, List<AlibabaTradeFastCargo>> supplierCargosMap = new HashMap<>();

        for (PurchasePlan purchasePlan : purchasePlans) {
            if (!supplierCargosMap.containsKey(purchasePlan.getSupplierName())) {
                supplierCargosMap.put(purchasePlan.getSupplierName(), new ArrayList<AlibabaTradeFastCargo>());
                supplierPurchasePlans.put(purchasePlan.getSupplierName(), new ArrayList<>());
            }
            supplierPurchasePlans.get(purchasePlan.getSupplierName()).add(purchasePlan.getId());
            for (ProductPurchaseInfo productPurchaseInfo : productPurchaseInfos) {
                if (productPurchaseInfo.getPurchaseSku().equals(purchasePlan.getPurchaseSku())) {
                    AlibabaTradeFastCargo alibabaTradeFastCargo = new AlibabaTradeFastCargo();
                    alibabaTradeFastCargo.setOfferId(Long.parseLong(productPurchaseInfo.getPurchaseLink()));
                    alibabaTradeFastCargo.setSpecId(productPurchaseInfo.getSpecId());
                    alibabaTradeFastCargo.setQuantity(purchasePlan.getQuantity().doubleValue());
                    supplierCargosMap.get(purchasePlan.getSupplierName()).add(alibabaTradeFastCargo);
                }
            }
        }

        AlibabaApiVo alibabaApiVo = (AlibabaApiVo) redisTemplate.opsForValue().get(RedisKey.STRING_ALI1688_API);

        List<Long> orderIds = new ArrayList<>();
        for (Map.Entry<String, List<AlibabaTradeFastCargo>> map : supplierCargosMap.entrySet()) {
            List<AlibabaTradeFastCargo> tradeFastCargos = map.getValue();
            AlibabaTradeFastResult alibabaTradeFastResult = null;
            Long id = getNextPurchaseOrderId();
            String message = "下单号： " + id;
//            try {
//                alibabaTradeFastResult = Ali1688Service.createOrder(tradeFastCargos, alibabaApiVo,message);
//            } catch (CustomerException e) {
//                e.printStackTrace();
//                continue;
//            }

            orderIds.add(id);
            PurchaseOrder purchaseOrder = new PurchaseOrder(id,
                    alibabaTradeFastResult.getOrderId(),
                    BigDecimal.ZERO,
                    new BigDecimal((alibabaTradeFastResult.getPostFee().doubleValue() / 100)),
                    new BigDecimal(alibabaTradeFastResult.getTotalSuccessAmount().doubleValue() / 100),
                    BigDecimal.ZERO,
                    map.getKey(),
                    0, 0, session.getId(), 0);

            List<PurchaseOrderItem> purchaseItems = new ArrayList<>();
            Double purchaseQuantity = 0.0;
            for (AlibabaTradeFastCargo tradeFastCargo : tradeFastCargos) {
                PurchasePlan purchasePlan = skuPurchasePlanMap.get(tradeFastCargo.getSpecId());
                if (null == purchasePlan) {
                    continue;
                }
                PurchaseOrderItem purchaseItem = new PurchaseOrderItem();
                BeanUtils.copyProperties(purchasePlan, purchaseItem);
                purchaseItem.setOriginalPrice(purchasePlan.getPrice());
                purchaseItem.setOriginalQuantity(purchasePlan.getQuantity());

                purchaseItem.setOrderId(id);
                purchaseItem.setId(IdGenerate.nextId());
                purchaseItems.add(purchaseItem);
                purchaseQuantity += tradeFastCargo.getQuantity();
            }
            purchaseOrder.setPurchaseQuantity(purchaseQuantity.intValue());
            List<Integer> planIds = supplierPurchasePlans.get(map.getKey());
            purchaseOrderService.insert(purchaseOrder);
            purchaseOrderItemService.insertByBatch(purchaseItems);
            purchasePlanService.updatePurchaseOrderIdByIds(planIds,id);
        }
        variantWarehouseStockService.updateVariantPurchaseStockByPlan(purchasePlans);
        RedisUtil.unLock(redisTemplate,key);
        return orderIds;
    }

    private Long getNextPurchaseOrderId(){
        String key = "purchase:order:no:latest";
        Long no = (Long) redisTemplate.opsForValue().get(key);
        if(null == no){
            no = 10001L;
        }else {
            no += 1;
        }
        redisTemplate.opsForValue().set(key,no);
        return no;
    }
}
