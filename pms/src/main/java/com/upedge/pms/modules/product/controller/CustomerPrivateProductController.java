package com.upedge.pms.modules.product.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.product.entity.CustomerPrivateProduct;
import com.upedge.pms.modules.product.service.CustomerPrivateProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.product.request.CustomerPrivateProductAddRequest;
import com.upedge.pms.modules.product.request.CustomerPrivateProductListRequest;
import com.upedge.pms.modules.product.request.CustomerPrivateProductUpdateRequest;

import com.upedge.pms.modules.product.response.CustomerPrivateProductAddResponse;
import com.upedge.pms.modules.product.response.CustomerPrivateProductDelResponse;
import com.upedge.pms.modules.product.response.CustomerPrivateProductInfoResponse;
import com.upedge.pms.modules.product.response.CustomerPrivateProductListResponse;
import com.upedge.pms.modules.product.response.CustomerPrivateProductUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/customerPrivateProduct")
public class CustomerPrivateProductController {
    @Autowired
    private CustomerPrivateProductService customerPrivateProductService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "product:customerprivateproduct:info:id")
    public CustomerPrivateProductInfoResponse info(@PathVariable Long id) {
        CustomerPrivateProduct result = customerPrivateProductService.selectByPrimaryKey(id);
        CustomerPrivateProductInfoResponse res = new CustomerPrivateProductInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "product:customerprivateproduct:list")
    public CustomerPrivateProductListResponse list(@RequestBody @Valid CustomerPrivateProductListRequest request) {
        List<CustomerPrivateProduct> results = customerPrivateProductService.select(request);
        Long total = customerPrivateProductService.count(request);
        request.setTotal(total);
        CustomerPrivateProductListResponse res = new CustomerPrivateProductListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "product:customerprivateproduct:add")
    public CustomerPrivateProductAddResponse add(@RequestBody @Valid CustomerPrivateProductAddRequest request) {
        CustomerPrivateProduct entity=request.toCustomerPrivateProduct();
        customerPrivateProductService.insertSelective(entity);
        CustomerPrivateProductAddResponse res = new CustomerPrivateProductAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:customerprivateproduct:del:id")
    public CustomerPrivateProductDelResponse del(@PathVariable Long id) {
        customerPrivateProductService.deleteByPrimaryKey(id);
        CustomerPrivateProductDelResponse res = new CustomerPrivateProductDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "product:customerprivateproduct:update")
    public CustomerPrivateProductUpdateResponse update(@PathVariable Long id,@RequestBody @Valid CustomerPrivateProductUpdateRequest request) {
        CustomerPrivateProduct entity=request.toCustomerPrivateProduct(id);
        customerPrivateProductService.updateByPrimaryKeySelective(entity);
        CustomerPrivateProductUpdateResponse res = new CustomerPrivateProductUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
