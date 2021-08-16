package com.upedge.ums.modules.organization.controller;

import java.util.Arrays;
import java.util.Map;

import com.upedge.common.constant.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.common.component.annotation.Permission;
import com.upedge.ums.modules.organization.entity.Organization;
import com.upedge.ums.modules.organization.service.OrganizationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.upedge.common.constant.Constant;
import com.upedge.ums.modules.organization.request.OrganizationAddRequest;
import com.upedge.ums.modules.organization.request.OrganizationListRequest;
import com.upedge.ums.modules.organization.request.OrganizationUpdateRequest;

import com.upedge.ums.modules.organization.response.OrganizationAddResponse;
import com.upedge.ums.modules.organization.response.OrganizationDelResponse;
import com.upedge.ums.modules.organization.response.OrganizationInfoResponse;
import com.upedge.ums.modules.organization.response.OrganizationListResponse;
import com.upedge.ums.modules.organization.response.OrganizationUpdateResponse;
import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {
    @Autowired
    private OrganizationService organizationService;


    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "organization:organization:info:id")
    public OrganizationInfoResponse info(@PathVariable Long id) {
        Organization result = organizationService.selectByPrimaryKey(id);
        OrganizationInfoResponse res = new OrganizationInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "organization:organization:list")
    public OrganizationListResponse list(@RequestBody @Valid OrganizationListRequest request) {
        List<Organization> results = organizationService.select(request);
        Long total = organizationService.count(request);
        request.setTotal(total);
        OrganizationListResponse res = new OrganizationListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "organization:organization:add")
    public OrganizationAddResponse add(@RequestBody @Valid OrganizationAddRequest request) {
        Organization entity=request.toOrganization();
        organizationService.insertSelective(entity);
        OrganizationAddResponse res = new OrganizationAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "organization:organization:del:id")
    public OrganizationDelResponse del(@PathVariable Long id) {
        organizationService.deleteByPrimaryKey(id);
        OrganizationDelResponse res = new OrganizationDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "organization:organization:update")
    public OrganizationUpdateResponse update(@PathVariable Long id,@RequestBody @Valid OrganizationUpdateRequest request) {
        Organization entity=request.toOrganization(id);
        organizationService.updateByPrimaryKeySelective(entity);
        OrganizationUpdateResponse res = new OrganizationUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
