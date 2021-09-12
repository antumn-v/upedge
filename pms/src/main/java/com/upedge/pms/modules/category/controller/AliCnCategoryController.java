package com.upedge.pms.modules.category.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.category.entity.AliCnCategory;
import com.upedge.pms.modules.category.service.AliCnCategoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.category.request.AliCnCategoryAddRequest;
import com.upedge.pms.modules.category.request.AliCnCategoryListRequest;
import com.upedge.pms.modules.category.request.AliCnCategoryUpdateRequest;

import com.upedge.pms.modules.category.response.AliCnCategoryAddResponse;
import com.upedge.pms.modules.category.response.AliCnCategoryDelResponse;
import com.upedge.pms.modules.category.response.AliCnCategoryInfoResponse;
import com.upedge.pms.modules.category.response.AliCnCategoryListResponse;
import com.upedge.pms.modules.category.response.AliCnCategoryUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/aliCnCategory")
public class AliCnCategoryController {
    @Autowired
    private AliCnCategoryService aliCnCategoryService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "category:alicncategory:info:id")
    public AliCnCategoryInfoResponse info(@PathVariable Integer id) {
        AliCnCategory result = aliCnCategoryService.selectByPrimaryKey(id);
        AliCnCategoryInfoResponse res = new AliCnCategoryInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "category:alicncategory:list")
    public AliCnCategoryListResponse list(@RequestBody @Valid AliCnCategoryListRequest request) {
        List<AliCnCategory> results = aliCnCategoryService.select(request);
        Long total = aliCnCategoryService.count(request);
        request.setTotal(total);
        AliCnCategoryListResponse res = new AliCnCategoryListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "category:alicncategory:add")
    public AliCnCategoryAddResponse add(@RequestBody @Valid AliCnCategoryAddRequest request) {
        AliCnCategory entity=request.toAliCnCategory();
        aliCnCategoryService.insertSelective(entity);
        AliCnCategoryAddResponse res = new AliCnCategoryAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "category:alicncategory:del:id")
    public AliCnCategoryDelResponse del(@PathVariable Integer id) {
        aliCnCategoryService.deleteByPrimaryKey(id);
        AliCnCategoryDelResponse res = new AliCnCategoryDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "category:alicncategory:update")
    public AliCnCategoryUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid AliCnCategoryUpdateRequest request) {
        AliCnCategory entity=request.toAliCnCategory(id);
        aliCnCategoryService.updateByPrimaryKeySelective(entity);
        AliCnCategoryUpdateResponse res = new AliCnCategoryUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
