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
import com.upedge.pms.modules.purchase.service.ProductPurchaseInfoService;
import com.upedge.pms.modules.purchase.service.PurchaseService;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    ProductVariantService productVariantService;

    @Autowired
    ProductPurchaseInfoService productPurchaseInfoService;

    @Autowired
    VariantWarehouseStockService variantWarehouseStockService;

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

        List<String> purchaseSkus = new ArrayList<>();
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
                if (purchaseAdviceItemVo.getVariantId().equals(productVariant)){
                    BeanUtils.copyProperties(productVariant,purchaseAdviceItemVo);
                    productVariants.remove(productVariant);
                    continue a;
                }
            }
        }

        List<ProductPurchaseInfo> productPurchaseInfos = productPurchaseInfoService.selectByPurchaseSkus(purchaseSkus);
        Map<String, PurchaseAdviceVo> purchaseAdviceVoMap = new HashMap<>();

        return null;
    }

    @Override
    public BaseResponse previewPurchaseOrder() {
        return null;
    }

    @Override
    public BaseResponse createPurchaseOrder() {
        return null;
    }
}
