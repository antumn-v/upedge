package com.upedge.pms.modules.category.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.category.entity.CategoryMapping;
import com.upedge.pms.modules.category.service.CategoryMappingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.category.request.CategoryMappingAddRequest;
import com.upedge.pms.modules.category.request.CategoryMappingListRequest;
import com.upedge.pms.modules.category.request.CategoryMappingUpdateRequest;

import com.upedge.pms.modules.category.response.CategoryMappingAddResponse;
import com.upedge.pms.modules.category.response.CategoryMappingDelResponse;
import com.upedge.pms.modules.category.response.CategoryMappingInfoResponse;
import com.upedge.pms.modules.category.response.CategoryMappingListResponse;
import com.upedge.pms.modules.category.response.CategoryMappingUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/categoryMapping")
public class CategoryMappingController {
    @Autowired
    private CategoryMappingService categoryMappingService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "category:categorymapping:info:id")
    public CategoryMappingInfoResponse info(@PathVariable Long id) {
        CategoryMapping result = categoryMappingService.selectByPrimaryKey(id);
        CategoryMappingInfoResponse res = new CategoryMappingInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "category:categorymapping:list")
    public CategoryMappingListResponse list(@RequestBody @Valid CategoryMappingListRequest request) {
        List<CategoryMapping> results = categoryMappingService.select(request);
        Long total = categoryMappingService.count(request);
        request.setTotal(total);
        CategoryMappingListResponse res = new CategoryMappingListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "category:categorymapping:add")
    public CategoryMappingAddResponse add(@RequestBody @Valid CategoryMappingAddRequest request) {
        CategoryMapping entity=request.toCategoryMapping();
        categoryMappingService.insertSelective(entity);
        CategoryMappingAddResponse res = new CategoryMappingAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "category:categorymapping:del:id")
    public CategoryMappingDelResponse del(@PathVariable Long id) {
        categoryMappingService.deleteByPrimaryKey(id);
        CategoryMappingDelResponse res = new CategoryMappingDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "category:categorymapping:update")
    public CategoryMappingUpdateResponse update(@PathVariable Long id,@RequestBody @Valid CategoryMappingUpdateRequest request) {
        CategoryMapping entity=request.toCategoryMapping(id);
        categoryMappingService.updateByPrimaryKeySelective(entity);
        CategoryMappingUpdateResponse res = new CategoryMappingUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
