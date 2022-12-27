package com.upedge.pms.modules.purchase.service.impl;

import com.alibaba.trade.param.*;
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
import com.upedge.common.model.user.vo.UserVo;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.purchase.entity.*;
import com.upedge.pms.modules.purchase.request.PurchaseAdviceRequest;
import com.upedge.pms.modules.purchase.request.PurchaseOrderCreateRequest;
import com.upedge.pms.modules.purchase.service.*;
import com.upedge.pms.modules.purchase.vo.CustomerPurchaseCountVo;
import com.upedge.pms.modules.purchase.vo.PurchaseOrderVo;
import com.upedge.thirdparty.ali1688.service.Ali1688Service;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
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
    public BaseResponse customerPurchaseCount() {
        OrderItemPurchaseAdviceVo purchaseAdviceVo = omsFeignClient.purchaseItems(new OrderItemPurchaseAdviceDto());
        if (purchaseAdviceVo == null) {
            return BaseResponse.success(new ArrayList<>());
        }
        Map<Long, CustomerPurchaseCountVo> customerPurchaseCountVoMap = new HashMap<>();
        List<PurchaseAdviceItemVo> purchaseAdviceItemVos = purchaseAdviceVo.getPurchaseAdviceItemVos();
        if (ListUtils.isEmpty(purchaseAdviceItemVos)){
            return BaseResponse.success(new ArrayList<>());
        }
        List<Long> planingVariantIds = purchasePlanService.selectPlaningVariantIds();
        for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
            Long variantId = purchaseAdviceItemVo.getVariantId();
            if (planingVariantIds.contains(variantId)){
                continue;
            }
            Integer quantity = purchaseAdviceItemVo.getOrderQuantity();
            Long customerId = purchaseAdviceItemVo.getCustomerId();

            CustomerPurchaseCountVo customerPurchaseCountVo = customerPurchaseCountVoMap.get(customerId);
            if (customerPurchaseCountVo == null){
                UserVo userVo = (UserVo) redisTemplate.opsForHash().get(RedisKey.STRING_CUSTOMER_INFO,customerId.toString());
                customerPurchaseCountVo = new CustomerPurchaseCountVo();
                customerPurchaseCountVo.setUsername(userVo.getUsername());
                customerPurchaseCountVo.setCustomerId(customerId);
                customerPurchaseCountVo.setTotal(0);
                customerPurchaseCountVoMap.put(customerId,customerPurchaseCountVo);
            }

            Integer total = customerPurchaseCountVo.getTotal();
            VariantWarehouseStock variantWarehouseStock = variantWarehouseStockService.selectByPrimaryKey(variantId,"CNHZ");
            if (variantWarehouseStock != null){
                quantity = quantity- variantWarehouseStock.getPurchaseStock() - variantWarehouseStock.getAvailableStock() ;
            }
            if (quantity > 0){
                total += quantity;
            }

            customerPurchaseCountVo.setTotal(total);
        }

        List<CustomerPurchaseCountVo> customerPurchaseCountVos = new ArrayList<>();
        customerPurchaseCountVoMap.forEach((customerId,itemVo) -> {
            if (itemVo.getTotal() > 0){
                customerPurchaseCountVos.add(itemVo);
            }
        });

        return BaseResponse.success(customerPurchaseCountVos);
    }

    @Override
    public BaseResponse cancelPurchase(List<Long> variantIds, Session session) {
        redisTemplate.opsForList().leftPushAll(RedisKey.STRING_VARIANT_CANCEL_PURCHASE_LIST, variantIds);
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
        redisTemplate.opsForList().leftPushAll(RedisKey.STRING_VARIANT_CANCEL_PURCHASE_LIST, cancelPurchaseVariantIds);
        return BaseResponse.success();
    }

    @Override
    public BaseResponse purchaseAdvice() {
        String warehouseCode = "CNHZ";
        redisTemplate.delete(RedisKey.HASH_PURCHASE_ADVICE_LIST);
        OrderItemPurchaseAdviceVo purchaseAdviceVo = omsFeignClient.purchaseItems(null);
        if (purchaseAdviceVo == null) {
            return BaseResponse.success(new ArrayList<>());
        }

        List<PurchaseAdviceItemVo> purchaseAdviceItemVos = purchaseAdviceVo.getPurchaseAdviceItemVos();
        if (ListUtils.isNotEmpty(purchaseAdviceItemVos)) {
            List<Long> planingVariantIds = purchasePlanService.selectPlaningVariantIds();
            if (null == planingVariantIds) {
                planingVariantIds = new ArrayList<>();
            }
            for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
                if (!planingVariantIds.contains(purchaseAdviceItemVo.getVariantId()))
                    redisTemplate.opsForHash().put(RedisKey.HASH_PURCHASE_ADVICE_LIST, purchaseAdviceItemVo.getVariantId().toString(), purchaseAdviceItemVo);
            }
        }

        List<Long> variantIds = new ArrayList<>();
        for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
            variantIds.add(purchaseAdviceItemVo.getVariantId());
        }


        List<Long> cancelPurchaseVariantIds = redisTemplate.opsForList().range(RedisKey.STRING_VARIANT_CANCEL_PURCHASE_LIST, 0, -1);
        List<Long> shuntPurchaseVariantIds = redisTemplate.opsForList().range(RedisKey.STRING_VARIANT_SHUNT_PURCHASE_LIST, 0, -1);
        variantIds.removeAll(cancelPurchaseVariantIds);
        variantIds.removeAll(shuntPurchaseVariantIds);

        List<PurchaseAdviceVo> adviceVos = getPurchaseAdvices(variantIds, warehouseCode, purchaseAdviceItemVos);

        return BaseResponse.success(adviceVos);
    }

    @Override
    public BaseResponse purchaseAdvice(PurchaseAdviceRequest request) {
        String warehouseCode = "CNHZ";
        Integer type = request.getType();
        OrderItemPurchaseAdviceVo purchaseAdviceVo = omsFeignClient.purchaseItems(request);
        if (purchaseAdviceVo == null) {
            return BaseResponse.success(new ArrayList<>());
        }

        List<PurchaseAdviceItemVo> purchaseAdviceItemVos = purchaseAdviceVo.getPurchaseAdviceItemVos();
        if (ListUtils.isNotEmpty(purchaseAdviceItemVos)) {
            List<Long> planingVariantIds = purchasePlanService.selectPlaningVariantIds();
            if (null == planingVariantIds) {
                planingVariantIds = new ArrayList<>();
            }
            List<PurchaseAdviceItemVo> removeItems = new ArrayList<>();
            for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
                if (planingVariantIds.contains(purchaseAdviceItemVo.getVariantId())){
                    removeItems.add(purchaseAdviceItemVo);
                }
            }
            purchaseAdviceItemVos.removeAll(removeItems);
        }

        List<Long> variantIds = new ArrayList<>();
        for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
            variantIds.add(purchaseAdviceItemVo.getVariantId());
        }


        List<Long> cancelPurchaseVariantIds = redisTemplate.opsForList().range(RedisKey.STRING_VARIANT_CANCEL_PURCHASE_LIST, 0, -1);
        List<Long> shuntPurchaseVariantIds = redisTemplate.opsForList().range(RedisKey.STRING_VARIANT_SHUNT_PURCHASE_LIST, 0, -1);
        variantIds.removeAll(cancelPurchaseVariantIds);
        variantIds.removeAll(shuntPurchaseVariantIds);
        switch (type){
            case 0:
                variantIds = cancelPurchaseVariantIds;
                break;
            case 1:
                break;
            case -1:
                variantIds = shuntPurchaseVariantIds;
                break;
            default:
                break;
        }

        List<PurchaseAdviceVo> adviceVos = getPurchaseAdvices(variantIds, warehouseCode, purchaseAdviceItemVos);

        return BaseResponse.success(adviceVos);
    }

    @Override
    public BaseResponse purchaseAdviceCache(PurchaseAdviceRequest request) {
        Integer type = request.getType();
        List<Long> variantIds = new ArrayList<>();

        OrderItemPurchaseAdviceDto orderItemPurchaseAdviceDto = request;
        if (null == orderItemPurchaseAdviceDto) {
            orderItemPurchaseAdviceDto = new OrderItemPurchaseAdviceDto();
        }
        String barcode = request.getBarcode();
        String variantSku = request.getVariantSku();
        ProductVariant productVariant = productVariantService.selectByBarcode(barcode);
        if (null == productVariant) {
            productVariant = productVariantService.selectBySku(variantSku);
        }
        if (null != productVariant) {
            variantIds.add(productVariant.getId());
        } else {
            variantIds = omsFeignClient.selectItemAdminVariantIds(orderItemPurchaseAdviceDto);
        }
        List<Long> planingVariantIds = purchasePlanService.selectPlaningVariantIds();
        variantIds.removeAll(planingVariantIds);
        if (ListUtils.isEmpty(variantIds)) {
            return BaseResponse.success();
        }
        List<Long> cancelPurchaseVariantIds = redisTemplate.opsForList().range(RedisKey.STRING_VARIANT_CANCEL_PURCHASE_LIST, 0, -1);
        List<Long> shuntPurchaseVariantIds = redisTemplate.opsForList().range(RedisKey.STRING_VARIANT_SHUNT_PURCHASE_LIST, 0, -1);
        List<Long> removeIds = new ArrayList<>();
        switch (type) {
            case 0://取消采购的产品
                if (ListUtils.isEmpty(cancelPurchaseVariantIds)) {
                    variantIds.clear();
                    break;
                }
                for (Long variantId : variantIds) {
                    if (!cancelPurchaseVariantIds.contains(variantId)) {
                        removeIds.add(variantId);
                    }
                }
                break;
            case 1://正常采购的产品
                variantIds.removeAll(cancelPurchaseVariantIds);
                variantIds.removeAll(shuntPurchaseVariantIds);
                break;
            case -1://搁置采购的产品
                if (ListUtils.isEmpty(shuntPurchaseVariantIds)) {
                    variantIds.clear();
                    break;
                }
                for (Long variantId : variantIds) {
                    if (!shuntPurchaseVariantIds.contains(variantId)) {
                        removeIds.add(variantId);
                    }
                }
                break;
        }
        variantIds.removeAll(removeIds);
        if (ListUtils.isEmpty(variantIds)) {
            return BaseResponse.success();
        }
        List<PurchaseAdviceItemVo> purchaseAdviceItemVos = new ArrayList<>();
        variantIds.forEach(variantId -> {
            if (variantId != null && redisTemplate.opsForHash().hasKey(RedisKey.HASH_PURCHASE_ADVICE_LIST, variantId.toString())) {
                PurchaseAdviceItemVo purchaseAdviceItemVo = (PurchaseAdviceItemVo) redisTemplate.opsForHash().get(RedisKey.HASH_PURCHASE_ADVICE_LIST, variantId.toString());
                if (null != purchaseAdviceItemVo) {
                    purchaseAdviceItemVos.add(purchaseAdviceItemVo);
                }
            }
        });
        List<PurchaseAdviceVo> adviceVos = getPurchaseAdvices(variantIds, "CNHZ", purchaseAdviceItemVos);
        return BaseResponse.success(adviceVos);
    }

    private List<PurchaseAdviceVo> getPurchaseAdvices(List<Long> variantIds, String warehouseCode, List<PurchaseAdviceItemVo> purchaseAdviceItemVos) {
        if (ListUtils.isEmpty(variantIds) || ListUtils.isEmpty(purchaseAdviceItemVos)) {
            return new ArrayList<>();
        }
        if (StringUtils.isBlank(warehouseCode)) {
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
            if (!variantIds.contains(variantId)) {
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

                    if (purchaseAdviceItemVo.getOrderQuantity() - purchaseAdviceItemVo.getPurchaseQuantity() - purchaseAdviceItemVo.getAvailableStock() < 1) {
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
                if (variantIds.contains(purchaseAdviceItemVo.getVariantId())) {
                    String noSupplier = "缺少供应商信息";
                    if (!purchaseAdviceVoMap.containsKey(noSupplier)) {
                        PurchaseAdviceVo purchaseAdviceVo = new PurchaseAdviceVo();
                        purchaseAdviceVo.setSupplierName(noSupplier);
                        purchaseAdviceVo.setWarehouseCode(warehouseCode);
                        purchaseAdviceVoMap.put(noSupplier, purchaseAdviceVo);
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
                    purchaseAdviceItemVo.setPurchaseLink(productPurchaseInfo.getPurchaseLink());
                    purchaseAdviceItemVo.setAliInventory(productPurchaseInfo.getInventory());
                    purchaseAdviceItemVo.setMinOrderQuantity(productPurchaseInfo.getMinOrderQuantity());
                    purchaseAdviceItemVo.setMixWholeSale(productPurchaseInfo.getMixWholeSale());
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
                AlibabaCreateOrderPreviewResult alibabaCreateOrderPreviewResult = Ali1688Service.createOrderPreview(map.getValue(), alibabaApiVo);
                List<AlibabaCreateOrderPreviewResultModel> previewResultModels = Arrays.asList(alibabaCreateOrderPreviewResult.getOrderPreviewResuslt());
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
                        purchasePlanService.updatePriceById(purchasePlan.getId(), price);
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


    @Override
    public List<Long> createPurchaseOrder(PurchaseOrderCreateRequest request, Session session) throws CustomerException {
//        String key = "key:createPurchaseOrder";
//        boolean b = RedisUtil.lock(redisTemplate, key, 10L, 60 * 1000L);
//        if (!b) {
//            throw new CustomerException("其他采购单正在生成中，请稍候。");
//        }

        List<PurchasePlan> purchasePlans = purchasePlanService.selectByIds(request.getIds());
        if (ListUtils.isEmpty(purchasePlans)) {
            return new ArrayList<>();
        }

        Map<String, PurchasePlan> skuPurchasePlanMap = new HashMap<>();
        Set<String> purchaseSkus = new HashSet<>();
        for (PurchasePlan purchasePlan : purchasePlans) {
            purchaseSkus.add(purchasePlan.getPurchaseSku());
            skuPurchasePlanMap.put(purchasePlan.getSpecId() + purchasePlan.getPurchaseLink(), purchasePlan);
        }
//        List<ProductPurchaseInfo> productPurchaseInfos = productPurchaseInfoService.selectByPurchaseSkus(purchaseSkus);

        Map<String, List<Integer>> supplierPurchasePlans = new HashMap<>();
        Map<String, List<AlibabaTradeFastCargo>> supplierCargosMap = new HashMap<>();

        for (PurchasePlan purchasePlan : purchasePlans) {
            String supplierName = purchasePlan.getSupplierName();
            if (!supplierCargosMap.containsKey(supplierName)) {
                supplierCargosMap.put(supplierName, new ArrayList<>());
                supplierPurchasePlans.put(supplierName, new ArrayList<>());
            }
            supplierPurchasePlans.get(supplierName).add(purchasePlan.getId());

            AlibabaTradeFastCargo alibabaTradeFastCargo = new AlibabaTradeFastCargo();
            alibabaTradeFastCargo.setOfferId(Long.parseLong(purchasePlan.getPurchaseLink()));
            alibabaTradeFastCargo.setSpecId(purchasePlan.getSpecId());
            alibabaTradeFastCargo.setQuantity(purchasePlan.getQuantity().doubleValue());
            supplierCargosMap.get(supplierName).add(alibabaTradeFastCargo);
        }

        AlibabaApiVo alibabaApiVo = (AlibabaApiVo) redisTemplate.opsForValue().get(RedisKey.STRING_ALI1688_API);

        List<Long> orderIds = new ArrayList<>();
        for (Map.Entry<String, List<AlibabaTradeFastCargo>> map : supplierCargosMap.entrySet()) {
            List<PurchasePlan> purchasePlanList = new ArrayList<>();

            List<AlibabaTradeFastCargo> tradeFastCargos = map.getValue();
            AlibabaTradeFastResult alibabaTradeFastResult = null;
            AlibabaTradeFastCreateOrderResult result = null;
            Long id = getNextPurchaseOrderId();
            String message = "下单号： " + id;

            result = Ali1688Service.createOrder(tradeFastCargos, alibabaApiVo, message);
            if (!result.getSuccess()){
                continue;
            }
            alibabaTradeFastResult = result.getResult();

            if (alibabaTradeFastResult == null) {
                continue;
            }

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
                PurchasePlan purchasePlan = skuPurchasePlanMap.get(tradeFastCargo.getSpecId()+tradeFastCargo.getOfferId());
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
                purchasePlanList.add(purchasePlan);
            }
            purchaseOrder.setPurchaseQuantity(purchaseQuantity.intValue());
            List<Integer> planIds = supplierPurchasePlans.get(map.getKey());
            purchaseOrderService.insert(purchaseOrder);
            purchaseOrderItemService.insertByBatch(purchaseItems);
            purchasePlanService.updatePurchaseOrderIdByIds(planIds, id);
            variantWarehouseStockService.updateVariantPurchaseStockByPlan(purchasePlanList);

        }
//        RedisUtil.unLock(redisTemplate, key);
        return orderIds;
    }

    @Override
    public BaseResponse createPurchaseOrder(PurchaseOrderCreateRequest request, Session session,Integer i)  {

        List<PurchasePlan> purchasePlans = purchasePlanService.selectByIds(request.getIds());
        if (ListUtils.isEmpty(purchasePlans)) {
            return BaseResponse.failed();
        }
        List<String> purchaseLinks = new ArrayList<>();
        Map<String,List<PurchasePlan>> supplierPlanMap = new HashMap<>();
        for (PurchasePlan purchasePlan : purchasePlans) {
            String supplierName = purchasePlan.getSupplierName();
            if (!supplierPlanMap.containsKey(supplierName)){
                supplierPlanMap.put(supplierName,new ArrayList<>());
            }
            supplierPlanMap.get(supplierName).add(purchasePlan);
            if (!purchaseLinks.contains(purchasePlan.getPurchaseLink())){
                purchaseLinks.add(purchasePlan.getPurchaseLink());
            }
        }

        supplierPlanMap.forEach((supplierName,plans) -> {
            Long id = getNextPurchaseOrderId();
            Integer purchaseQuantity = 0;
            PurchaseOrder purchaseOrder = new PurchaseOrder(id,
                    null,
                    BigDecimal.ZERO,
                    null,
                    null,
                    BigDecimal.ZERO,
                    supplierName,
                    0, 0, session.getId(), 0);

            List<PurchaseOrderItem> purchaseItems = new ArrayList<>();
            List<Integer> planIds = new ArrayList<>();
            for (PurchasePlan purchasePlan : plans) {
                planIds.add(purchasePlan.getId());
                PurchaseOrderItem purchaseItem = new PurchaseOrderItem();
                BeanUtils.copyProperties(purchasePlan, purchaseItem);
                purchaseItem.setOriginalPrice(purchasePlan.getPrice());
                purchaseItem.setOriginalQuantity(purchasePlan.getQuantity());
                purchaseItem.setOrderId(id);
                purchaseItem.setId(IdGenerate.nextId());
                purchaseItems.add(purchaseItem);

                purchaseQuantity += purchasePlan.getQuantity();
            }
            purchaseOrder.setPurchaseQuantity(purchaseQuantity);
            purchaseOrderService.insert(purchaseOrder);
            purchaseOrderItemService.insertByBatch(purchaseItems);
            purchasePlanService.updatePurchaseOrderIdByIds(planIds, id);
            variantWarehouseStockService.updateVariantPurchaseStockByPlan(plans);
        });

        CompletableFuture.runAsync(new Runnable() {
            @Override
            public void run() {
                for (String purchaseLink : purchaseLinks) {
                    productPurchaseInfoService.refreshAlibabaProductInventory(purchaseLink);
                }
            }
        },threadPoolExecutor);
        return BaseResponse.success();
    }

    @Override
    public Long getNextPurchaseOrderId() {
        String key = "purchase:order:no:latest";
        Long no = (Long) redisTemplate.opsForValue().get(key);
        if (null == no) {
            no = 10001L;
        } else {
            no += 1;
        }
        redisTemplate.opsForValue().set(key, no);
        return no;
    }
}
