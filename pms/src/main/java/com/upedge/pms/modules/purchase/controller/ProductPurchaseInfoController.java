package com.upedge.pms.modules.purchase.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.pms.modules.purchase.dto.OfferInventoryChangeListDTO;
import com.upedge.pms.modules.purchase.entity.ProductPurchaseInfo;
import com.upedge.pms.modules.purchase.request.ProductPurchaseInfoAddRequest;
import com.upedge.pms.modules.purchase.request.ProductPurchaseInfoListRequest;
import com.upedge.pms.modules.purchase.request.ProductPurchaseInfoUpdateRequest;
import com.upedge.pms.modules.purchase.request.ProductVariantSyncInventoryRequest;
import com.upedge.pms.modules.purchase.response.*;
import com.upedge.pms.modules.purchase.service.ProductPurchaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/productPurchaseInfo")
public class ProductPurchaseInfoController {
    @Autowired
    private ProductPurchaseInfoService productPurchaseInfoService;


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
    public void syncPurchaseVariantInventory(@RequestBody ProductVariantSyncInventoryRequest request){
        if (request.getData() == null){
            return;
        }
        List<OfferInventoryChangeListDTO> offerInventoryChangeListDTOS = request.getData().getOfferInventoryChangeList();
        productPurchaseInfoService.syncPurchaseInventory(offerInventoryChangeListDTOS);
    }


}
