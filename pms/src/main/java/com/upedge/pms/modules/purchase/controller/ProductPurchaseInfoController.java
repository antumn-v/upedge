package com.upedge.pms.modules.purchase.controller;

import com.alibaba.fastjson.JSONObject;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.pms.dto.VariantPurchaseInfoDto;
import com.upedge.common.model.product.AlibabaApiVo;
import com.upedge.common.utils.ListUtils;
import com.upedge.pms.modules.alibaba.service.Ali1688Service;
import com.upedge.pms.modules.purchase.dto.OfferInventoryChangeListDTO;
import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import com.upedge.pms.modules.purchase.request.ProductPurchaseInfoAddRequest;
import com.upedge.pms.modules.purchase.request.ProductPurchaseInfoListRequest;
import com.upedge.pms.modules.purchase.request.ProductPurchaseInfoUpdateRequest;
import com.upedge.pms.modules.purchase.request.ProductVariantSyncInventoryRequest;
import com.upedge.pms.modules.purchase.response.*;
import com.upedge.pms.modules.purchase.service.ProductPurchaseInfoService;
import com.upedge.thirdparty.ali1688.entity.product.ProductSaleInfo;
import com.upedge.thirdparty.ali1688.vo.AlibabaProductVo;
import com.upedge.thirdparty.ali1688.vo.ProductVariantAttrVo;
import com.upedge.thirdparty.ali1688.vo.ProductVariantVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 *
 * @author gx
 */
@Slf4j
@RestController
@RequestMapping("/productPurchaseInfo")
public class ProductPurchaseInfoController {
    @Autowired
    private ProductPurchaseInfoService productPurchaseInfoService;

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("/variants")
    public List<VariantPurchaseInfoDto> variantPurchaseInfo(@RequestBody List<Long> variantIds){
        return productPurchaseInfoService.selectByVariantIds(variantIds);
    }


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "purchase:productpurchaseinfo:info:id")
    public ProductPurchaseInfoInfoResponse info(@PathVariable String id) {
        ProductPurchaseInfo result = productPurchaseInfoService.selectByPrimaryKey(id);
        ProductPurchaseInfoInfoResponse res = new ProductPurchaseInfoInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "purchase:productpurchaseinfo:list")
    public ProductPurchaseInfoListResponse list(@RequestBody @Valid ProductPurchaseInfoListRequest request) {
        List<ProductPurchaseInfo> results = productPurchaseInfoService.select(request);
        Long total = productPurchaseInfoService.count(request);
        request.setTotal(total);
        ProductPurchaseInfoListResponse res = new ProductPurchaseInfoListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "purchase:productpurchaseinfo:add")
    public ProductPurchaseInfoAddResponse add(@RequestBody @Valid ProductPurchaseInfoAddRequest request) {
        ProductPurchaseInfo entity=request.toProductPurchaseInfo();
        productPurchaseInfoService.insertSelective(entity);
        ProductPurchaseInfoAddResponse res = new ProductPurchaseInfoAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:productpurchaseinfo:del:id")
    public ProductPurchaseInfoDelResponse del(@PathVariable String id) {
        productPurchaseInfoService.deleteByPrimaryKey(id);
        ProductPurchaseInfoDelResponse res = new ProductPurchaseInfoDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:productpurchaseinfo:update")
    public ProductPurchaseInfoUpdateResponse update(@PathVariable String id,@RequestBody @Valid ProductPurchaseInfoUpdateRequest request) {
        ProductPurchaseInfo entity=request.toProductPurchaseInfo(id);
        productPurchaseInfoService.updateByPrimaryKeySelective(entity);
        ProductPurchaseInfoUpdateResponse res = new ProductPurchaseInfoUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @PostMapping("/syncInventory")
    public void syncPurchaseVariantInventory(String message){
        if (StringUtils.isBlank(message)){
            return;
        }
        ProductVariantSyncInventoryRequest request = null;
        try {
            request = JSONObject.parseObject(message, ProductVariantSyncInventoryRequest.class);
        } catch (Exception e) {
            log.warn("syncInventory: {}",message);
        }

        List<OfferInventoryChangeListDTO> offerInventoryChangeListDTOS = request.getData().getOfferInventoryChangeList();
        productPurchaseInfoService.syncPurchaseInventory(offerInventoryChangeListDTOS);
    }


    @PostMapping("/save/{aliProductId}")
    public BaseResponse save(@PathVariable String aliProductId){
        AlibabaApiVo alibabaApiVo = (AlibabaApiVo) redisTemplate.opsForValue().get(RedisKey.STRING_ALI1688_API);
        AlibabaProductVo alibabaProductVo = Ali1688Service.getProduct(aliProductId, alibabaApiVo,false);
        if (alibabaProductVo == null){
            return BaseResponse.failed();
        }
        List<ProductVariantVo> skuInfos = alibabaProductVo.getProductVariantVoList();
        if (ListUtils.isEmpty(skuInfos)){
            return BaseResponse.failed();
        }
        String supplierName = alibabaProductVo.getSupplierVo().getSupplierName();
        ProductSaleInfo productSaleInfo = alibabaProductVo.getSaleInfo();
        List<ProductPurchaseInfo> productPurchaseInfos = new ArrayList<>();
        for (ProductVariantVo skuInfo : skuInfos) {
            String sku = skuInfo.getVariantSku();
            ProductPurchaseInfo productPurchaseInfo = productPurchaseInfoService.selectByPrimaryKey(sku);
            if (null != productPurchaseInfo){
                continue;
            }
            productPurchaseInfo = new ProductPurchaseInfo();
            List<String> cnNameList = skuInfo.getVariantAttrVoList().stream().map(ProductVariantAttrVo::getVariantAttrCvalue).collect(Collectors.toList());
            productPurchaseInfo.setPurchaseSku(sku);
            productPurchaseInfo.setVariantImage(skuInfo.getVariantImage());
            productPurchaseInfo.setVariantName(cnNameList.toString());
            productPurchaseInfo.setPurchaseLink(aliProductId);
            productPurchaseInfo.setSupplierName(supplierName);
            productPurchaseInfo.setSpecId(skuInfo.getSpecId());
            productPurchaseInfo.setInventory(skuInfo.getInventory());
            productPurchaseInfo.setMinOrderQuantity(productSaleInfo.getMinOrderQuantity());
            productPurchaseInfo.setMixWholeSale(productSaleInfo.getMixWholeSale());
            productPurchaseInfos.add(productPurchaseInfo);
        }
        productPurchaseInfoService.insertByBatch(productPurchaseInfos);
        return BaseResponse.success();
    }


}
