package com.upedge.ums.modules.store.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.store.entity.Store;
import com.upedge.ums.modules.store.service.StoreService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.store.request.StoreAddRequest;
import com.upedge.ums.modules.store.request.StoreListRequest;
import com.upedge.ums.modules.store.request.StoreUpdateRequest;

import com.upedge.ums.modules.store.response.StoreAddResponse;
import com.upedge.ums.modules.store.response.StoreDelResponse;
import com.upedge.ums.modules.store.response.StoreInfoResponse;
import com.upedge.ums.modules.store.response.StoreListResponse;
import com.upedge.ums.modules.store.response.StoreUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "客户店铺管理")
@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "store:store:info:id")
    public StoreInfoResponse info(@PathVariable Long id) {
        Store result = storeService.selectByPrimaryKey(id);
        StoreInfoResponse res = new StoreInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @ApiOperation("客户店铺列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "store:store:list")
    public StoreListResponse list(@RequestBody @Valid StoreListRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        if (request.getT() == null){
            request.setT(new Store());
        }
        request.getT().setCustomerId(session.getCustomerId());
        List<Store> results = storeService.select(request);
        Long total = storeService.count(request);
        request.setTotal(total);
        StoreListResponse res = new StoreListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "store:store:add")
    public StoreAddResponse add(@RequestBody @Valid StoreAddRequest request) {
        Store entity=request.toStore();
        storeService.insertSelective(entity);
        StoreAddResponse res = new StoreAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "store:store:del:id")
    public StoreDelResponse del(@PathVariable Long id) {
        storeService.deleteByPrimaryKey(id);
        StoreDelResponse res = new StoreDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "store:store:update")
    public StoreUpdateResponse update(@PathVariable Long id,@RequestBody @Valid StoreUpdateRequest request) {
        Store entity=request.toStore(id);
        storeService.updateByPrimaryKeySelective(entity);
        StoreUpdateResponse res = new StoreUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
