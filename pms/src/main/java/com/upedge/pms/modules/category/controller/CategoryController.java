package com.upedge.pms.modules.category.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.pms.modules.category.entity.Category;
import com.upedge.pms.modules.category.service.CategoryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.pms.modules.category.request.CategoryAddRequest;
import com.upedge.pms.modules.category.request.CategoryListRequest;
import com.upedge.pms.modules.category.request.CategoryUpdateRequest;

import com.upedge.pms.modules.category.response.CategoryAddResponse;
import com.upedge.pms.modules.category.response.CategoryDelResponse;
import com.upedge.pms.modules.category.response.CategoryInfoResponse;
import com.upedge.pms.modules.category.response.CategoryListResponse;
import com.upedge.pms.modules.category.response.CategoryUpdateResponse;
import javax.validation.Valid;

/**
 * 类目
 *
 * @author gx
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "category:category:info:id")
    public CategoryInfoResponse info(@PathVariable Long id) {
        Category result = categoryService.selectByPrimaryKey(id);
        CategoryInfoResponse res = new CategoryInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "category:category:list")
    public CategoryListResponse list(@RequestBody @Valid CategoryListRequest request) {
        List<Category> results = categoryService.select(request);
        Long total = categoryService.count(request);
        request.setTotal(total);
        CategoryListResponse res = new CategoryListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "category:category:add")
    public CategoryAddResponse add(@RequestBody @Valid CategoryAddRequest request) {
        Category entity=request.toCategory();
        categoryService.insertSelective(entity);
        CategoryAddResponse res = new CategoryAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "category:category:del:id")
    public CategoryDelResponse del(@PathVariable Long id) {
        categoryService.deleteByPrimaryKey(id);
        CategoryDelResponse res = new CategoryDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "category:category:update")
    public CategoryUpdateResponse update(@PathVariable Long id,@RequestBody @Valid CategoryUpdateRequest request) {
        Category entity=request.toCategory(id);
        categoryService.updateByPrimaryKeySelective(entity);
        CategoryUpdateResponse res = new CategoryUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
