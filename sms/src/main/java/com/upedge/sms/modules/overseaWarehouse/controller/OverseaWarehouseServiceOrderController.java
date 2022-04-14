package com.upedge.sms.modules.overseaWarehouse.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrder;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderCreateRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderListRequest;
import com.upedge.sms.modules.overseaWarehouse.response.OverseaWarehouseServiceOrderListResponse;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderService;
import com.upedge.sms.modules.overseaWarehouse.vo.OverseaWarehouseServiceOrderVo;
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
@Api(tags = "海外仓订单")
@RestController
@RequestMapping("/overseaWarehouseServiceOrder")
public class OverseaWarehouseServiceOrderController {
    @Autowired
    private OverseaWarehouseServiceOrderService overseaWarehouseServiceOrderService;

    @Autowired
    RedisTemplate redisTemplate;


    @ApiOperation("订单详情")
    @GetMapping("/detail/{id}")
    public BaseResponse orderDetail(@PathVariable Long id){
        OverseaWarehouseServiceOrderVo overseaWarehouseServiceOrderVo = overseaWarehouseServiceOrderService.orderDetail(id);
        return BaseResponse.success(overseaWarehouseServiceOrderVo);
    }

    @ApiOperation("创建海外仓服务订单")
    @PostMapping("/create")
    public BaseResponse createOverseaWarehouseService(@RequestBody@Valid OverseaWarehouseServiceOrderCreateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return overseaWarehouseServiceOrderService.create(request,session);
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorder:list")
    public OverseaWarehouseServiceOrderListResponse list(@RequestBody @Valid OverseaWarehouseServiceOrderListRequest request) {
        List<OverseaWarehouseServiceOrder> results = overseaWarehouseServiceOrderService.select(request);
        Long total = overseaWarehouseServiceOrderService.count(request);
        request.setTotal(total);
        OverseaWarehouseServiceOrderListResponse res = new OverseaWarehouseServiceOrderListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }




}
