package com.upedge.sms.modules.wholesale.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.sms.modules.wholesale.WholesaleOrderVo;
import com.upedge.sms.modules.wholesale.entity.WholesaleOrder;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderAddRequest;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderCreateRequest;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderListRequest;
import com.upedge.sms.modules.wholesale.request.WholesaleOrderUpdateRequest;
import com.upedge.sms.modules.wholesale.response.*;
import com.upedge.sms.modules.wholesale.service.WholesaleOrderService;
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
@Api(tags = "批发订单")
@RestController
@RequestMapping("/wholesaleOrder")
public class WholesaleOrderController {
    @Autowired
    private WholesaleOrderService wholesaleOrderService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("创建批发订单")
    @PostMapping("/create")
    public BaseResponse createWarehouse(@RequestBody@Valid WholesaleOrderCreateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return wholesaleOrderService.create(request,session);
    }

    @ApiOperation("批发订单详情")
    @RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
    @Permission(permission = "wholesale:wholesaleorder:info:id")
    public WholesaleOrderInfoResponse info(@PathVariable Long id) {
        WholesaleOrderVo wholesaleOrderVo = wholesaleOrderService.orderDetail(id);
        WholesaleOrderInfoResponse res = new WholesaleOrderInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,wholesaleOrderVo,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorder:list")
    public WholesaleOrderListResponse list(@RequestBody @Valid WholesaleOrderListRequest request) {
        List<WholesaleOrder> results = wholesaleOrderService.select(request);
        Long total = wholesaleOrderService.count(request);
        request.setTotal(total);
        WholesaleOrderListResponse res = new WholesaleOrderListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorder:add")
    public WholesaleOrderAddResponse add(@RequestBody @Valid WholesaleOrderAddRequest request) {
        WholesaleOrder entity=request.toWholesaleOrder();
        wholesaleOrderService.insertSelective(entity);
        WholesaleOrderAddResponse res = new WholesaleOrderAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorder:del:id")
    public WholesaleOrderDelResponse del(@PathVariable Long id) {
        wholesaleOrderService.deleteByPrimaryKey(id);
        WholesaleOrderDelResponse res = new WholesaleOrderDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "wholesale:wholesaleorder:update")
    public WholesaleOrderUpdateResponse update(@PathVariable Long id,@RequestBody @Valid WholesaleOrderUpdateRequest request) {
        WholesaleOrder entity=request.toWholesaleOrder(id);
        wholesaleOrderService.updateByPrimaryKeySelective(entity);
        WholesaleOrderUpdateResponse res = new WholesaleOrderUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
