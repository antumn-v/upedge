package com.upedge.pms.modules.inventory.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.product.ProductSaiheInventoryVo;
import com.upedge.pms.modules.inventory.entity.ProductSaiheInventory;
import com.upedge.pms.modules.inventory.request.ProductSaiheInventoryAddRequest;
import com.upedge.pms.modules.inventory.request.ProductSaiheInventoryUpdateRequest;
import com.upedge.pms.modules.inventory.request.SaiheInvebtoryRequest;
import com.upedge.pms.modules.inventory.response.ProductSaiheInventoryAddResponse;
import com.upedge.pms.modules.inventory.response.ProductSaiheInventoryDelResponse;
import com.upedge.pms.modules.inventory.response.ProductSaiheInventoryInfoResponse;
import com.upedge.pms.modules.inventory.response.ProductSaiheInventoryUpdateResponse;
import com.upedge.pms.modules.inventory.service.ProductSaiheInventoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/productSaiheInventory")
public class ProductSaiheInventoryController {
    @Autowired
    private ProductSaiheInventoryService productSaiheInventoryService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "inventory:productsaiheinventory:info:id")
    public ProductSaiheInventoryInfoResponse info(@PathVariable String id) {
        ProductSaiheInventory result = productSaiheInventoryService.selectByPrimaryKey(id);
        ProductSaiheInventoryInfoResponse res = new ProductSaiheInventoryInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    /*@RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "inventory:productsaiheinventory:list")
    public ProductSaiheInventoryListResponse list(@RequestBody @Valid ProductSaiheInventoryListRequest request) {
        List<ProductSaiheInventory> results = productSaiheInventoryService.select(request);
        Long total = productSaiheInventoryService.count(request);
        request.setTotal(total);
        ProductSaiheInventoryListResponse res = new ProductSaiheInventoryListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }*/

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "inventory:productsaiheinventory:add")
    public ProductSaiheInventoryAddResponse add(@RequestBody @Valid ProductSaiheInventoryAddRequest request) {
        ProductSaiheInventory entity=request.toProductSaiheInventory();
        productSaiheInventoryService.insertSelective(entity);
        ProductSaiheInventoryAddResponse res = new ProductSaiheInventoryAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "inventory:productsaiheinventory:del:id")
    public ProductSaiheInventoryDelResponse del(@PathVariable String id) {
        productSaiheInventoryService.deleteByPrimaryKey(id);
        ProductSaiheInventoryDelResponse res = new ProductSaiheInventoryDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "inventory:productsaiheinventory:update")
    public ProductSaiheInventoryUpdateResponse update(@PathVariable String id, @RequestBody @Valid ProductSaiheInventoryUpdateRequest request) {
        ProductSaiheInventory entity=request.toProductSaiheInventory(id);
        productSaiheInventoryService.updateByPrimaryKeySelective(entity);
        ProductSaiheInventoryUpdateResponse res = new ProductSaiheInventoryUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


    /**
     * 从赛盒同步
     */
    @ApiOperation("客户库存分析 从赛盒同步")
    @RequestMapping(value = "/batchRefreshInventory")
    @Permission(permission = "inventory:productsaiheinventory:list")
    public BaseResponse batchRefreshInventory() throws CustomerException {
        productSaiheInventoryService.batchRefreshInventory();
        return BaseResponse.success();
    }

    /**
     *客户库存分析列表
     * @param request
     * @return
     */
//    @ApiOperation("客户库存分析列表")
//    @RequestMapping(value="/list", method=RequestMethod.POST)
//    @Permission(permission = "inventory:productsaiheinventory:list")
//    public BaseResponse list(@RequestBody @Valid SaiheInvebtoryRequest request) {
//        return  productSaiheInventoryService.selectProductSaiheInventoryPage(request);
//    }

    /**
     *客户库存分析列表   所有库存金额
     * @param request
     * @return
     */
//    @ApiOperation("客户库存分析列表 所有库存金额")
//    @RequestMapping(value="/list/sumMoney", method=RequestMethod.POST)
//    @Permission(permission = "inventory:productsaiheinventory:sumMoney")
//    public BaseResponse sumMoney(@RequestBody @Valid SaiheInvebtoryRequest request) {
//        return  productSaiheInventoryService.selectProductSaiheInventoryListSumMoney(request);
//    }

    @RequestMapping(value = "/queryProductSaiheInventory" , method = RequestMethod.POST)
    public BaseResponse queryProductSaiheInventory(@RequestBody ProductSaiheInventoryVo productSaiheInventory){
        List<ProductSaiheInventoryVo> queryProductSaiheInventory= productSaiheInventoryService.queryProductSaiheInventory(productSaiheInventory);
        return BaseResponse.success(queryProductSaiheInventory);
    }

    @RequestMapping(value = "/insertProductSaiheInventory" , method = RequestMethod.POST)
    BaseResponse insertProductSaiheInventory(@RequestBody ProductSaiheInventoryVo productSaiheInventory){
        productSaiheInventoryService.insertProductSaiheInventory(productSaiheInventory);
        return BaseResponse.success();
    }

}
