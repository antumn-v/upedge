package com.upedge.pms.modules.supplier.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.pms.modules.supplier.entity.Supplier;
import com.upedge.pms.modules.supplier.request.SupplierAddRequest;
import com.upedge.pms.modules.supplier.request.SupplierListRequest;
import com.upedge.pms.modules.supplier.request.SupplierUpdateRequest;
import com.upedge.pms.modules.supplier.response.SupplierAddResponse;
import com.upedge.pms.modules.supplier.response.SupplierInfoResponse;
import com.upedge.pms.modules.supplier.response.SupplierListResponse;
import com.upedge.pms.modules.supplier.response.SupplierUpdateResponse;
import com.upedge.pms.modules.supplier.service.SupplierService;
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
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "supplier:supplier:info:id")
    public SupplierInfoResponse info(@PathVariable Integer id) {
        Supplier result = supplierService.selectByPrimaryKey(id);
        SupplierInfoResponse res = new SupplierInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "supplier:supplier:list")
    public SupplierListResponse list(@RequestBody @Valid SupplierListRequest request) {
        List<Supplier> results = supplierService.select(request);
        Long total = supplierService.count(request);
        request.setTotal(total);
        SupplierListResponse res = new SupplierListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "supplier:supplier:add")
    public SupplierAddResponse add(@RequestBody @Valid SupplierAddRequest request) {
        Supplier entity=request.toSupplier();
        supplierService.insertSelective(entity);
        SupplierAddResponse res = new SupplierAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }


    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "supplier:supplier:update")
    public SupplierUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid SupplierUpdateRequest request) {
        Supplier entity=request.toSupplier(id);
        supplierService.updateByPrimaryKeySelective(entity);
        SupplierUpdateResponse res = new SupplierUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
