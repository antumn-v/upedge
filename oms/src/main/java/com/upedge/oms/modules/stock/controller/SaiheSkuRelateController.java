package com.upedge.oms.modules.stock.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.oms.modules.stock.entity.SaiheSkuRelate;
import com.upedge.oms.modules.stock.service.SaiheSkuRelateService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.oms.modules.stock.request.SaiheSkuRelateAddRequest;
import com.upedge.oms.modules.stock.request.SaiheSkuRelateListRequest;
import com.upedge.oms.modules.stock.request.SaiheSkuRelateUpdateRequest;

import com.upedge.oms.modules.stock.response.SaiheSkuRelateAddResponse;
import com.upedge.oms.modules.stock.response.SaiheSkuRelateDelResponse;
import com.upedge.oms.modules.stock.response.SaiheSkuRelateInfoResponse;
import com.upedge.oms.modules.stock.response.SaiheSkuRelateListResponse;
import com.upedge.oms.modules.stock.response.SaiheSkuRelateUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/saiheSkuRelate")
public class SaiheSkuRelateController {
    @Autowired
    private SaiheSkuRelateService saiheSkuRelateService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "stock:saiheskurelate:info:id")
    public SaiheSkuRelateInfoResponse info(@PathVariable String id) {
        SaiheSkuRelate result = saiheSkuRelateService.selectByPrimaryKey(id);
        SaiheSkuRelateInfoResponse res = new SaiheSkuRelateInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "stock:saiheskurelate:list")
    public SaiheSkuRelateListResponse list(@RequestBody @Valid SaiheSkuRelateListRequest request) {
        List<SaiheSkuRelate> results = saiheSkuRelateService.select(request);
        Long total = saiheSkuRelateService.count(request);
        request.setTotal(total);
        SaiheSkuRelateListResponse res = new SaiheSkuRelateListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "stock:saiheskurelate:add")
    public SaiheSkuRelateAddResponse add(@RequestBody @Valid SaiheSkuRelateAddRequest request) {
        SaiheSkuRelate entity=request.toSaiheSkuRelate();
        saiheSkuRelateService.insertSelective(entity);
        SaiheSkuRelateAddResponse res = new SaiheSkuRelateAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "stock:saiheskurelate:del:id")
    public SaiheSkuRelateDelResponse del(@PathVariable String id) {
        saiheSkuRelateService.deleteByPrimaryKey(id);
        SaiheSkuRelateDelResponse res = new SaiheSkuRelateDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "stock:saiheskurelate:update")
    public SaiheSkuRelateUpdateResponse update(@PathVariable String id,@RequestBody @Valid SaiheSkuRelateUpdateRequest request) {
        SaiheSkuRelate entity=request.toSaiheSkuRelate(id);
        saiheSkuRelateService.updateByPrimaryKeySelective(entity);
        SaiheSkuRelateUpdateResponse res = new SaiheSkuRelateUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
