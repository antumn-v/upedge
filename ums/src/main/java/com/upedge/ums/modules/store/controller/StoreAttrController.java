package com.upedge.ums.modules.store.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.ums.modules.store.entity.StoreAttr;
import com.upedge.ums.modules.store.request.StoreAttrAddRequest;
import com.upedge.ums.modules.store.request.StoreAttrListRequest;
import com.upedge.ums.modules.store.request.StoreAttrUpdateRequest;
import com.upedge.ums.modules.store.response.*;
import com.upedge.ums.modules.store.service.StoreAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/storeAttr")
public class StoreAttrController {
    @Autowired
    private StoreAttrService storeAttrService;

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "store:storeattr:info:id")
    public StoreAttrInfoResponse info(@PathVariable Integer id) {
        StoreAttr result = storeAttrService.selectByPrimaryKey(id);
        StoreAttrInfoResponse res = new StoreAttrInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "store:storeattr:list")
    public StoreAttrListResponse list(@RequestBody @Valid StoreAttrListRequest request) {
        List<StoreAttr> results = storeAttrService.select(request);
        Long total = storeAttrService.count(request);
        request.setTotal(total);
        StoreAttrListResponse res = new StoreAttrListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "store:storeattr:add")
    public StoreAttrAddResponse add(@RequestBody @Valid StoreAttrAddRequest request) {
        StoreAttr entity=request.toStoreAttr();
        storeAttrService.insertSelective(entity);
        StoreAttrAddResponse res = new StoreAttrAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "store:storeattr:del:id")
    public StoreAttrDelResponse del(@PathVariable Integer id) {
        storeAttrService.deleteByPrimaryKey(id);
        StoreAttrDelResponse res = new StoreAttrDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "store:storeattr:update")
    public StoreAttrUpdateResponse update(@PathVariable Integer id, @RequestBody @Valid StoreAttrUpdateRequest request) {
        StoreAttr entity=request.toStoreAttr(id);
        storeAttrService.updateByPrimaryKeySelective(entity);
        StoreAttrUpdateResponse res = new StoreAttrUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
