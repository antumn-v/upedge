package com.upedge.ums.modules.store.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.store.entity.StoreSetting;
import com.upedge.ums.modules.store.service.StoreSettingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.store.request.StoreSettingAddRequest;
import com.upedge.ums.modules.store.request.StoreSettingListRequest;
import com.upedge.ums.modules.store.request.StoreSettingUpdateRequest;

import com.upedge.ums.modules.store.response.StoreSettingAddResponse;
import com.upedge.ums.modules.store.response.StoreSettingDelResponse;
import com.upedge.ums.modules.store.response.StoreSettingInfoResponse;
import com.upedge.ums.modules.store.response.StoreSettingListResponse;
import com.upedge.ums.modules.store.response.StoreSettingUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/storeSetting")
public class StoreSettingController {
    @Autowired
    private StoreSettingService storeSettingService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "store:storesetting:info:id")
    public StoreSettingInfoResponse info(@PathVariable Integer id) {
        StoreSetting result = storeSettingService.selectByPrimaryKey(id);
        StoreSettingInfoResponse res = new StoreSettingInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "store:storesetting:list")
    public StoreSettingListResponse list(@RequestBody @Valid StoreSettingListRequest request) {
        List<StoreSetting> results = storeSettingService.select(request);
        Long total = storeSettingService.count(request);
        request.setTotal(total);
        StoreSettingListResponse res = new StoreSettingListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "store:storesetting:add")
    public StoreSettingAddResponse add(@RequestBody @Valid StoreSettingAddRequest request) {
        StoreSetting entity=request.toStoreSetting();
        storeSettingService.insertSelective(entity);
        StoreSettingAddResponse res = new StoreSettingAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "store:storesetting:del:id")
    public StoreSettingDelResponse del(@PathVariable Integer id) {
        storeSettingService.deleteByPrimaryKey(id);
        StoreSettingDelResponse res = new StoreSettingDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "store:storesetting:update")
    public StoreSettingUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid StoreSettingUpdateRequest request) {
        StoreSetting entity=request.toStoreSetting(id);
        storeSettingService.updateByPrimaryKeySelective(entity);
        StoreSettingUpdateResponse res = new StoreSettingUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
