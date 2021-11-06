package com.upedge.oms.modules.pack.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.pack.request.AnalysisLogisticsRequest;
import com.upedge.oms.modules.pack.request.PackageTrackingListRequest;
import com.upedge.oms.modules.pack.request.PackageTrackingRefreshRequest;
import com.upedge.oms.modules.pack.service.PackageTrackingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/packageTracking")
public class PackageTrackingController {
    @Autowired
    private PackageTrackingService packageTrackingService;

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "pack:packagetracking:list")
    public BaseResponse list(@RequestBody @Valid PackageTrackingListRequest request) {
        return packageTrackingService.packageTrackingList(request);
    }

    /**
     * 客户管理-个人客户 点击客户id进入用户信息-物流分析
     */
    @ApiOperation(value="客户管理-个人客户 点击客户id进入用户信息-物流分析",notes="物流分析")
    @PostMapping("/analysisLogistics")
    public BaseResponse analysisLogistics(@RequestBody  AnalysisLogisticsRequest analysisLogistics) throws CustomerException {
        Session session = UserUtil.getSession(redisTemplate);
        return packageTrackingService.analysisLogistics(analysisLogistics,session);

    }


    @ApiOperation("包裹同步")
    @PostMapping("/refresh")
    public BaseResponse refreshPackageTracking(@RequestBody PackageTrackingRefreshRequest refreshRequest){
        return packageTrackingService.refreshTrackingState(refreshRequest);
    }
}
