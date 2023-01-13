package com.upedge.oms.modules.pack.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.oms.modules.pack.entity.OrderPackageBackup;
import com.upedge.oms.modules.pack.service.OrderPackageBackupService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.oms.modules.pack.request.OrderPackageBackupAddRequest;
import com.upedge.oms.modules.pack.request.OrderPackageBackupListRequest;
import com.upedge.oms.modules.pack.request.OrderPackageBackupUpdateRequest;

import com.upedge.oms.modules.pack.response.OrderPackageBackupAddResponse;
import com.upedge.oms.modules.pack.response.OrderPackageBackupDelResponse;
import com.upedge.oms.modules.pack.response.OrderPackageBackupInfoResponse;
import com.upedge.oms.modules.pack.response.OrderPackageBackupListResponse;
import com.upedge.oms.modules.pack.response.OrderPackageBackupUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/orderPackageBackup")
public class OrderPackageBackupController {
    @Autowired
    private OrderPackageBackupService orderPackageBackupService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "pack:orderpackagebackup:info:id")
    public OrderPackageBackupInfoResponse info(@PathVariable Long id) {
        OrderPackageBackup result = orderPackageBackupService.selectByPrimaryKey(id);
        OrderPackageBackupInfoResponse res = new OrderPackageBackupInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "pack:orderpackagebackup:list")
    public OrderPackageBackupListResponse list(@RequestBody @Valid OrderPackageBackupListRequest request) {
        List<OrderPackageBackup> results = orderPackageBackupService.select(request);
        Long total = orderPackageBackupService.count(request);
        request.setTotal(total);
        OrderPackageBackupListResponse res = new OrderPackageBackupListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "pack:orderpackagebackup:add")
    public OrderPackageBackupAddResponse add(@RequestBody @Valid OrderPackageBackupAddRequest request) {
        OrderPackageBackup entity=request.toOrderPackageBackup();
        orderPackageBackupService.insertSelective(entity);
        OrderPackageBackupAddResponse res = new OrderPackageBackupAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "pack:orderpackagebackup:del:id")
    public OrderPackageBackupDelResponse del(@PathVariable Long id) {
        orderPackageBackupService.deleteByPrimaryKey(id);
        OrderPackageBackupDelResponse res = new OrderPackageBackupDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "pack:orderpackagebackup:update")
    public OrderPackageBackupUpdateResponse update(@PathVariable Long id,@RequestBody @Valid OrderPackageBackupUpdateRequest request) {
        OrderPackageBackup entity=request.toOrderPackageBackup(id);
        orderPackageBackupService.updateByPrimaryKeySelective(entity);
        OrderPackageBackupUpdateResponse res = new OrderPackageBackupUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
