package com.upedge.ums.modules.user.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.user.entity.CustomerIoss;
import com.upedge.ums.modules.user.request.CustomerIossAddRequest;
import com.upedge.ums.modules.user.request.CustomerIossListRequest;
import com.upedge.ums.modules.user.request.CustomerIossUpdateRequest;
import com.upedge.ums.modules.user.response.*;
import com.upedge.ums.modules.user.service.CustomerIossService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "客户IOSS管理")
@RestController
@RequestMapping("/customerIoss")
public class CustomerIossController {

    @Autowired
    private CustomerIossService customerIossService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("用户IOSS详情")
    @GetMapping("/detail/{customerId}")
    public BaseResponse customerIossDetail(@PathVariable Long customerId){
        CustomerIoss customerIoss = customerIossService.selectByCustomerId(customerId);
        return BaseResponse.success(customerIoss);
    }

    @ApiOperation("根据ID查看IOSS详情")
    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "user:customerioss:info:id")
    public CustomerIossInfoResponse info(@PathVariable Long id) {
        CustomerIoss result = customerIossService.selectByPrimaryKey(id);
        CustomerIossInfoResponse res = new CustomerIossInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "user:customerioss:list")
    public CustomerIossListResponse list(@RequestBody @Valid CustomerIossListRequest request) {
        List<CustomerIoss> results = customerIossService.select(request);
        Long total = customerIossService.count(request);
        request.setTotal(total);
        CustomerIossListResponse res = new CustomerIossListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @ApiOperation("客户添加ioss")
    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "user:customerioss:add")
    public BaseResponse add(@RequestBody @Valid CustomerIossAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        CustomerIoss customerIoss = customerIossService.selectByCustomerId(session.getCustomerId());
        if (customerIoss != null){
            return BaseResponse.failed();
        }
        CustomerIoss entity=request.toCustomerIoss(session);
        customerIossService.insertSelective(entity);
        CustomerIossAddResponse res = new CustomerIossAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @ApiOperation("客户修改IOSS")
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "user:customerioss:update")
    public CustomerIossUpdateResponse update(@PathVariable Long id,@RequestBody @Valid CustomerIossUpdateRequest request) {
        CustomerIoss entity=request.toCustomerIoss(id);
        customerIossService.updateByPrimaryKeySelective(entity);
        CustomerIossUpdateResponse res = new CustomerIossUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("客户删除IOSS")
    @PostMapping("/remove/{id}")
    public BaseResponse removeCustomerIoss(@PathVariable Long id){
        customerIossService.deleteByPrimaryKey(id);
        return BaseResponse.success();
    }


}
