package com.upedge.pms.modules.alibaba.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.pms.modules.alibaba.service.AlibabaApiService;
import com.upedge.pms.modules.alibaba.entity.AlibabaApi;
import com.upedge.pms.modules.alibaba.request.AlibabaApiListRequest;
import com.upedge.pms.modules.alibaba.request.AlibabaApiUpdateRequest;
import com.upedge.pms.modules.alibaba.response.AlibabaApiDelResponse;
import com.upedge.pms.modules.alibaba.response.AlibabaApiInfoResponse;
import com.upedge.pms.modules.alibaba.response.AlibabaApiListResponse;
import com.upedge.pms.modules.alibaba.response.AlibabaApiUpdateResponse;
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
@RequestMapping("/alibabaApi")
public class AlibabaApiController {
    @Autowired
    private AlibabaApiService alibabaApiService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "alibaba:alibabaapi:info:id")
    public AlibabaApiInfoResponse info(@PathVariable String id) {
        AlibabaApi result = alibabaApiService.selectByPrimaryKey(id);
        AlibabaApiInfoResponse res = new AlibabaApiInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "alibaba:alibabaapi:list")
    public AlibabaApiListResponse list(@RequestBody @Valid AlibabaApiListRequest request) {
        List<AlibabaApi> results = alibabaApiService.select(request);
        Long total = alibabaApiService.count(request);
        request.setTotal(total);
        AlibabaApiListResponse res = new AlibabaApiListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }



    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "alibaba:alibabaapi:del:id")
    public AlibabaApiDelResponse del(@PathVariable String id) {
        alibabaApiService.deleteByPrimaryKey(id);
        AlibabaApiDelResponse res = new AlibabaApiDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "alibaba:alibabaapi:update")
    public AlibabaApiUpdateResponse update(@PathVariable String id,@RequestBody @Valid AlibabaApiUpdateRequest request) {
        AlibabaApi entity=request.toAlibabaApi(id);
        alibabaApiService.updateByPrimaryKeySelective(entity);
        AlibabaApiUpdateResponse res = new AlibabaApiUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
