package com.upedge.oms.modules.pack.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.utils.DateUtils;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.oms.modules.pack.request.*;
import com.upedge.oms.modules.pack.response.PackageInfoListQueryResponse;
import com.upedge.oms.modules.pack.response.PackageInfoListResponse;
import com.upedge.oms.modules.pack.response.PackageManagerResponse;
import com.upedge.oms.modules.pack.service.PackageDailyCountService;
import com.upedge.oms.modules.pack.service.PackageInfoService;
import com.upedge.oms.modules.pack.service.PackageUsdRateService;
import com.upedge.oms.modules.pack.vo.ManagerPackageCountVo;
import com.upedge.oms.modules.pack.vo.PackageCountVo;
import com.upedge.oms.modules.pack.vo.PackageDailyCountVo;
import com.upedge.oms.scheduler.PackageScheduler;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/packageInfo")
public class PackageInfoController {
    @Autowired
    private PackageInfoService packageInfoService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    PackageScheduler packageScheduler;

    @Autowired
    PackageDailyCountService packageDailyCountService;

    @Autowired
    PackageUsdRateService packageUsdRateService;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;

    @ApiOperation("包裹管理")
    @PostMapping("/manage")
    public BaseResponse packageManager(@RequestBody PackageDailyCountRequest request){
        if (request.getBeginTime() == null
        && request.getEndTime() == null
        && request.getManagerCode() == null
        && request.getDateMonth() == null){
            request.setDateMonth(DateUtils.formatDate(new Date(),"yyyy-MM"));
        }
        PackageManagerResponse.PackageManagerData packageManagerData = new PackageManagerResponse.PackageManagerData();
        //客户经理包裹统计
        CompletableFuture<Void> managerPackageCount = CompletableFuture.runAsync(() -> {
            List<ManagerPackageCountVo> managerPackageCountVos = packageDailyCountService.selectManagerPackageCount(request);
            packageManagerData.setManagerPackageCountVos(managerPackageCountVos);
        });
        //每日包裹数量
        CompletableFuture<Void> dailyPackageCount = CompletableFuture.runAsync(() -> {
            List<PackageDailyCountVo> packageDailyCountVos = packageDailyCountService.selectPackageDailyCount(request);
            packageManagerData.setPackageDailyCountVos(packageDailyCountVos);
        });
        //包裹统计
        CompletableFuture<Void> packageCount = CompletableFuture.runAsync(() -> {
            PackageCountVo packageCountVo = packageInfoService.selectPackageCount(request);
            packageManagerData.setPackageCountVo(packageCountVo);
        });

        BigDecimal usdRate = packageUsdRateService.currentMonthUsdRate();
        packageManagerData.setUsdRate(usdRate);
        try {
            CompletableFuture.allOf(managerPackageCount,dailyPackageCount,packageCount).get();
        } catch (Exception e) {
            e.printStackTrace();
            return new PackageManagerResponse(ResultCode.FAIL_CODE,"系统异常，请联系管理员");
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,packageManagerData,request);
    }

    /**
     * 包裹按日期更新
     * @param request
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public BaseResponse updatePackageInfo(@RequestBody @Valid PackageInfoUpdateRequest request){
        String key= RedisUtil.KEY_PACKAGE_UPDATE;
        boolean flag= RedisUtil.lock(redisTemplate,key,2L,1000L*5*60);
        //获取锁成功
        if(!flag){
            return new BaseResponse(ResultCode.FAIL_CODE,"操作中!不能重复请求!");
        }
        try {
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            int res = packageInfoService.updatePackageInfoByDate(request);
            if(res>0){
                return new BaseResponse(ResultCode.SUCCESS_CODE, "获取包裹信息成功!"+res);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            RedisUtil.unLock(redisTemplate,key);
        }
        return new BaseResponse(ResultCode.FAIL_CODE,"获取包裹失败!");
    }

    /**
     * 包裹报表数据 包裹数量按天统计 包裹订单数量客户经理分布 月包裹总数
     * @param request
     * @return
     */
    @RequestMapping(value = "/admin/charts",method = RequestMethod.POST)
    public BaseResponse packageCharts(@RequestBody @Valid PackageInfoQueryRequest request){
        return packageInfoService.packageCharts(request);
    }

    @ApiOperation("包裹信息列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "pack:packageinfo:list")
    public PackageInfoListResponse list(@RequestBody @Valid PackageInfoListRequest request) {
        return packageInfoService.adminList(request);
    }

    /**
     * 包裹列表数据
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/list", method=RequestMethod.POST)
    @Permission(permission = "pack:packageinfo:list")
    public PackageInfoListQueryResponse list(@RequestBody PackageInfoListQueryRequest request) {
        return packageInfoService.adminListV2(request);
    }

    @GetMapping("/pullTracking")
    public BaseResponse pullTracking(){
        packageScheduler.pullNormalTracking();
        return BaseResponse.success();
    }


}
