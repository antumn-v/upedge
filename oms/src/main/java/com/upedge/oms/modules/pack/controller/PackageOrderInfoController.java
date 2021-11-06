package com.upedge.oms.modules.pack.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.statistics.request.ManagerPackageStatisticsRequest;
import com.upedge.common.model.statistics.response.ManagerOrderCountResponse;
import com.upedge.common.model.statistics.vo.ManagerPackageOrderCountVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.pack.request.OrderHandleTimeRequest;
import com.upedge.oms.modules.pack.request.PackageOrderInfoListRequest;
import com.upedge.oms.modules.pack.response.PackageOrderInfoListResponse;
import com.upedge.oms.modules.pack.service.PackageOrderInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/packageOrderInfo")
public class PackageOrderInfoController {
    @Autowired
    private PackageOrderInfoService packageOrderInfoService;

    @Autowired
    RedisTemplate redisTemplate;

   /* *//**
     * 包裹订单列表
     * @param request
     * @return
     *//*
    @RequestMapping(value="/admin/list", method= RequestMethod.POST)
    @Permission(permission = "pack:packageorderinfo:list")
    public PackageOrderInfoListResponse list(@RequestBody @Valid PackageOrderInfoListRequest request) {
        return packageOrderInfoService.adminList(request);
    }*/

    /**
     * 包裹订单列表
     * @param request
     * @return
     */
    @ApiOperation("包裹订单列表")
    @RequestMapping(value="/list", method= RequestMethod.POST)
    @Permission(permission = "pack:packageorderinfo:list")
    public PackageOrderInfoListResponse list(@RequestBody @Valid PackageOrderInfoListRequest request) {
        return packageOrderInfoService.adminList(request);
    }
    @ApiOperation("包裹订单时效")
    @PostMapping("/handleTime")
    public BaseResponse orderHandleTimeCount(@RequestBody @Valid OrderHandleTimeRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return packageOrderInfoService.orderHandleTimeCount(request,session);
    }


    @PostMapping("/manager/orderCount")
    public ManagerOrderCountResponse managerDateOrderCount(ManagerPackageStatisticsRequest request){
        List<ManagerPackageOrderCountVo> managerPackageOrderCountVos = packageOrderInfoService.selectManagerPackageOrderCountByDate(request);
        return  new ManagerOrderCountResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,managerPackageOrderCountVos,request);
    }
}
