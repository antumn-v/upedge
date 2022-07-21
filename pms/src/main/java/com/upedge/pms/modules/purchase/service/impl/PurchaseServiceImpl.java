package com.upedge.pms.modules.purchase.service.impl;

import com.alibaba.trade.param.AlibabaCreateOrderPreviewResultCargoModel;
import com.alibaba.trade.param.AlibabaCreateOrderPreviewResultModel;
import com.alibaba.trade.param.AlibabaTradeFastCargo;
import com.alibaba.trade.param.AlibabaTradeFastResult;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.feign.OmsFeignClient;
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
import com.upedge.pms.modules.purchase.request.PurchaseOrderCreateRequest;
import com.upedge.pms.modules.purchase.service.*;
import com.upedge.pms.modules.purchase.vo.PurchaseOrderVo;
import com.upedge.thirdparty.ali1688.service.Ali1688Service;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

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


    @Override
    public BaseResponse purchaseAdvice(String warehouseCode) {

        List<PurchaseAdviceItemVo> purchaseAdviceItemVos = omsFeignClient.purchaseItems();
        if (ListUtils.isEmpty(purchaseAdviceItemVos)) {
            return BaseResponse.success(new ArrayList<>());
        }

        List<Long> variantIds = new ArrayList<>();
        for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
            variantIds.add(purchaseAdviceItemVo.getVariantId());
        }

        List<Long> planingVariantIds = purchasePlanService.selectPlaningVariantIds();
        if (ListUtils.isNotEmpty(planingVariantIds)) {
            variantIds.removeAll(planingVariantIds);
        }

        Set<String> purchaseSkus = new HashSet<>();
        List<ProductVariant> productVariants = productVariantService.listVariantByIds(variantIds);

        List<VariantWarehouseStock> variantWarehouseStocks = variantWarehouseStockService.selectByVariantIdsAndWarehouseCode(variantIds, warehouseCode);

        a:
        for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
            Long variantId = purchaseAdviceItemVo.getVariantId();
            b:
            for (VariantWarehouseStock variantWarehouseStock : variantWarehouseStocks) {
                if (variantWarehouseStock.getVariantId().equals(variantId)) {
                    purchaseAdviceItemVo.setSafeStock(variantWarehouseStock.getSafeStock());
                    purchaseAdviceItemVo.setPurchaseQuantity(variantWarehouseStock.getPurchaseStock());
                    variantWarehouseStocks.remove(variantWarehouseStock);
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

        List<ProductPurchaseInfo> productPurchaseInfos = productPurchaseInfoService.selectByPurchaseSkus(purchaseSkus);
        Map<String, PurchaseAdviceVo> purchaseAdviceVoMap = new HashMap<>();
        a:
        for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
            if (purchaseAdviceItemVo.getPurchaseSku() == null) {
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
            return BaseResponse.success(new ArrayList<>());
        }
        List<PurchaseAdviceVo> purchaseAdviceVos = new ArrayList<>();
        for (Map.Entry<String, PurchaseAdviceVo> map : purchaseAdviceVoMap.entrySet()) {
            purchaseAdviceVos.add(map.getValue());
        }
        return BaseResponse.success(purchaseAdviceVos);
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
                        PurchaseOrderItem purchaseItemVo = new PurchaseOrderItem();
                        BeanUtils.copyProperties(purchasePlan, purchaseItemVo);
                        purchaseItemVo.setPrice(new BigDecimal(resultCargoModel.getFinalUnitPrice()));
                        purchaseItemVos.add(purchaseItemVo);
                        purchaseOrderVo.setCargoPromotionList(Arrays.asList(resultCargoModel.getCargoPromotionList()));
                    }
                    purchaseOrderVo.setPurchaseItemVos(purchaseItemVos);
                    purchaseOrderVos.add(purchaseOrderVo);
                }
            } catch (CustomerException e) {
                e.printStackTrace();
                continue;
            }
        }

        return BaseResponse.success(purchaseOrderVos);
    }


    @Transactional
    @Override
    public BaseResponse createPurchaseOrder(PurchaseOrderCreateRequest request, Session session) {
        String key = "key:createPurchaseOrder";
        boolean b = RedisUtil.lock(redisTemplate,key,10L,60 * 1000L);
        if (!b){
            return BaseResponse.failed("其他采购单正在生成中，请稍候。");
        }

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

        for (Map.Entry<String, List<AlibabaTradeFastCargo>> map : supplierCargosMap.entrySet()) {
            List<AlibabaTradeFastCargo> tradeFastCargos = map.getValue();
            AlibabaTradeFastResult alibabaTradeFastResult = null;
            try {
                alibabaTradeFastResult = Ali1688Service.createOrder(tradeFastCargos, alibabaApiVo);
            } catch (CustomerException e) {
                e.printStackTrace();
                continue;
            }
            Long id = IdGenerate.nextId();
            PurchaseOrder purchaseOrder = new PurchaseOrder(id, alibabaTradeFastResult.getOrderId(),
                    BigDecimal.ZERO,
                    new BigDecimal((alibabaTradeFastResult.getPostFee().doubleValue() / 100)),
                    new BigDecimal(alibabaTradeFastResult.getTotalSuccessAmount().doubleValue() / 100),
                    BigDecimal.ZERO,
                    map.getKey(),
                    0, 0, session.getId());

            List<PurchaseOrderItem> purchaseItems = new ArrayList<>();
            for (AlibabaTradeFastCargo tradeFastCargo : tradeFastCargos) {
                PurchasePlan purchasePlan = skuPurchasePlanMap.get(tradeFastCargo.getSpecId());
                if (null == purchasePlan) {
                    continue;
                }
                PurchaseOrderItem purchaseItem = new PurchaseOrderItem();
                BeanUtils.copyProperties(purchasePlan, purchaseItem);
                purchaseItem.setVariantName(purchasePlan.getCnName());
                purchaseItem.setOrderId(id);
                purchaseItem.setId(IdGenerate.nextId());
                purchaseItems.add(purchaseItem);
            }
            List<Integer> planIds = supplierPurchasePlans.get(map.getKey());
            purchaseOrderService.insert(purchaseOrder);
            purchaseOrderItemService.insertByBatch(purchaseItems);
            purchasePlanService.updatePurchaseOrderIdByIds(planIds,id);
        }
        variantWarehouseStockService.updateVariantPurchaseStockByPlan(purchasePlans);
        RedisUtil.unLock(redisTemplate,key);
        return BaseResponse.success();
    }
}