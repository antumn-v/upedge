package com.upedge.pms.modules.purchase.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.pms.vo.PurchaseAdviceItemVo;
import com.upedge.common.model.pms.vo.PurchaseAdviceVo;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.product.entity.ProductVariant;
import com.upedge.pms.modules.product.service.ProductVariantService;
import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import com.upedge.pms.modules.purchase.request.PurchaseOrderCreateRequest;
import com.upedge.pms.modules.purchase.service.ProductPurchaseInfoService;
import com.upedge.pms.modules.purchase.service.PurchasePlanService;
import com.upedge.pms.modules.purchase.service.PurchaseService;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    OmsFeignClient omsFeignClient;


    @Override
    public BaseResponse purchaseAdvice(String warehouseCode) {

        List<PurchaseAdviceItemVo> purchaseAdviceItemVos = omsFeignClient.purchaseItems();
        if (ListUtils.isEmpty(purchaseAdviceItemVos)){
            return BaseResponse.success(new ArrayList<>());
        }

        List<Long> variantIds = new ArrayList<>();
        for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
            variantIds.add(purchaseAdviceItemVo.getVariantId());
        }

        List<Long> planingVariantIds = purchasePlanService.selectPlaningVariantIds();
        if (ListUtils.isNotEmpty(planingVariantIds)){
            variantIds.removeAll(planingVariantIds);
        }

        Set<String> purchaseSkus = new HashSet<>();
        List<ProductVariant> productVariants = productVariantService.listVariantByIds(variantIds);

        List<VariantWarehouseStock> variantWarehouseStocks = variantWarehouseStockService.selectByVariantIdsAndWarehouseCode(variantIds,warehouseCode);

        a:
        for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
            Long variantId = purchaseAdviceItemVo.getVariantId();
            b:
            for (VariantWarehouseStock variantWarehouseStock : variantWarehouseStocks) {
                if(variantWarehouseStock.getVariantId().equals(variantId)){
                    purchaseAdviceItemVo.setSafeStock(variantWarehouseStock.getSafeStock());
                    purchaseAdviceItemVo.setPurchaseQuantity(variantWarehouseStock.getPurchaseStock());
                    variantWarehouseStocks.remove(variantWarehouseStock);
                    break b;
                }
            }
            for (ProductVariant productVariant : productVariants) {
                purchaseSkus.add(productVariant.getPurchaseSku());
                if (purchaseAdviceItemVo.getVariantId().equals(productVariant.getId())){
                    BeanUtils.copyProperties(productVariant,purchaseAdviceItemVo);
                    productVariants.remove(productVariant);
                    continue a;
                }
            }
        }

        List<ProductPurchaseInfo> productPurchaseInfos = productPurchaseInfoService.selectByPurchaseSkus(purchaseSkus);
        Map<String, PurchaseAdviceVo> purchaseAdviceVoMap = new HashMap<>();
        a:
        for (PurchaseAdviceItemVo purchaseAdviceItemVo : purchaseAdviceItemVos) {
            if (purchaseAdviceItemVo.getPurchaseSku() == null){
                continue ;
            }
            for (ProductPurchaseInfo productPurchaseInfo : productPurchaseInfos) {
                String supplierName = productPurchaseInfo.getSupplierName();
                String purchaseSku = productPurchaseInfo.getPurchaseSku();
                if (purchaseAdviceItemVo.getPurchaseSku().equals(purchaseSku)){
                    PurchaseAdviceVo purchaseAdviceVo = purchaseAdviceVoMap.get(supplierName);
                    if (purchaseAdviceVo == null){
                        purchaseAdviceVo = new PurchaseAdviceVo();
                        purchaseAdviceVo.setSupplierName(supplierName);
                        purchaseAdviceVo.setWarehouseCode(warehouseCode);
                    }
                    purchaseAdviceVo.getPurchaseAdviceItemVos().add(purchaseAdviceItemVo);
                    purchaseAdviceVoMap.put(supplierName,purchaseAdviceVo);
                    continue a;
                }
            }
        }
        if (MapUtils.isEmpty(purchaseAdviceVoMap)){
            return BaseResponse.success(new ArrayList<>());
        }
        List<PurchaseAdviceVo> purchaseAdviceVos = new ArrayList<>();
        for (Map.Entry<String,PurchaseAdviceVo> map:purchaseAdviceVoMap.entrySet()){
            purchaseAdviceVos.add(map.getValue());
        }
        return BaseResponse.success(purchaseAdviceVos);
    }

    @Override
    public BaseResponse previewPurchaseOrder(PurchaseOrderCreateRequest request) {
        return null;
    }

    @Override
    public BaseResponse createPurchaseOrder() {
        return null;
    }
}
