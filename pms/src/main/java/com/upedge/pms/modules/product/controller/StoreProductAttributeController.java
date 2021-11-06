package com.upedge.pms.modules.product.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.pms.modules.product.entity.StoreProductAttribute;
import com.upedge.pms.modules.product.request.StoreProductAttributeAddRequest;
import com.upedge.pms.modules.product.request.StoreProductAttributeListRequest;
import com.upedge.pms.modules.product.request.StoreProductAttributeUpdateRequest;
import com.upedge.pms.modules.product.response.*;
import com.upedge.pms.modules.product.service.StoreProductAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author author
 */
//@RestController
//@RequestMapping("/storeProductAttribute")
public class StoreProductAttributeController {
    @Autowired
    private StoreProductAttributeService storeProductAttributeService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "app:storeproductattribute:info:id")
    public StoreProductAttributeInfoResponse info(@PathVariable Long id) {
        StoreProductAttribute result = storeProductAttributeService.selectByPrimaryKey(id);
        StoreProductAttributeInfoResponse res = new StoreProductAttributeInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "app:storeproductattribute:list")
    public StoreProductAttributeListResponse list(@RequestBody @Valid StoreProductAttributeListRequest request) {
        List<StoreProductAttribute> results = storeProductAttributeService.select(request);
        Long total = storeProductAttributeService.count(request);
        request.setTotal(total);
        StoreProductAttributeListResponse res = new StoreProductAttributeListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "app:storeproductattribute:add")
    public StoreProductAttributeAddResponse add(@RequestBody @Valid StoreProductAttributeAddRequest request) {
        StoreProductAttribute entity=request.toStoreProductAttribute();
        storeProductAttributeService.insertSelective(entity);
        StoreProductAttributeAddResponse res = new StoreProductAttributeAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "app:storeproductattribute:del:id")
    public StoreProductAttributeDelResponse del(@PathVariable Long id) {
        storeProductAttributeService.deleteByPrimaryKey(id);
        StoreProductAttributeDelResponse res = new StoreProductAttributeDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "app:storeproductattribute:update")
    public StoreProductAttributeUpdateResponse update(@PathVariable Long id, @RequestBody @Valid StoreProductAttributeUpdateRequest request) {
        StoreProductAttribute entity=request.toStoreProductAttribute(id);
        storeProductAttributeService.updateByPrimaryKeySelective(entity);
        StoreProductAttributeUpdateResponse res = new StoreProductAttributeUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
