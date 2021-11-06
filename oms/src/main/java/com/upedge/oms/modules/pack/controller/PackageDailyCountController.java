package com.upedge.oms.modules.pack.controller;

import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.statistics.request.ManagerPackageStatisticsRequest;
import com.upedge.common.model.statistics.response.ManagerPackageStatisticsResponse;
import com.upedge.common.model.statistics.vo.ManagerPackageStatisticsVo;
import com.upedge.oms.modules.pack.entity.PackageDailyCount;
import com.upedge.oms.modules.pack.request.PackageDailyCountAddRequest;
import com.upedge.oms.modules.pack.request.PackageDailyCountListRequest;
import com.upedge.oms.modules.pack.request.PackageDailyCountUpdateRequest;
import com.upedge.oms.modules.pack.response.*;
import com.upedge.oms.modules.pack.service.PackageDailyCountService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author gx
 */
@RestController
@RequestMapping("/packageDailyCount")
public class PackageDailyCountController {
    @Autowired
    private PackageDailyCountService packageDailyCountService;

    @ApiOperation("客户经理包裹统计")
    @PostMapping("/manager/statistics")
    public ManagerPackageStatisticsResponse managerPackageStatistics(@RequestBody ManagerPackageStatisticsRequest request){

        List<ManagerPackageStatisticsVo> managerPackageStatisticsVos = packageDailyCountService.selectManagerPackageStatistics(request);
        return new ManagerPackageStatisticsResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,managerPackageStatisticsVos);

    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "pack:packagedailycount:info:id")
    public PackageDailyCountInfoResponse info(@PathVariable Integer id) {
        PackageDailyCount result = packageDailyCountService.selectByPrimaryKey(id);
        PackageDailyCountInfoResponse res = new PackageDailyCountInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "pack:packagedailycount:list")
    public PackageDailyCountListResponse list(@RequestBody @Valid PackageDailyCountListRequest request) {
        List<PackageDailyCount> results = packageDailyCountService.select(request);
        Long total = packageDailyCountService.count(request);
        request.setTotal(total);
        PackageDailyCountListResponse res = new PackageDailyCountListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "pack:packagedailycount:add")
    public PackageDailyCountAddResponse add(@RequestBody @Valid PackageDailyCountAddRequest request) {
        PackageDailyCount entity=request.toPackageDailyCount();
        packageDailyCountService.insertSelective(entity);
        PackageDailyCountAddResponse res = new PackageDailyCountAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "pack:packagedailycount:del:id")
    public PackageDailyCountDelResponse del(@PathVariable Integer id) {
        packageDailyCountService.deleteByPrimaryKey(id);
        PackageDailyCountDelResponse res = new PackageDailyCountDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "pack:packagedailycount:update")
    public PackageDailyCountUpdateResponse update(@PathVariable Integer id, @RequestBody @Valid PackageDailyCountUpdateRequest request) {
        PackageDailyCount entity=request.toPackageDailyCount(id);
        packageDailyCountService.updateByPrimaryKeySelective(entity);
        PackageDailyCountUpdateResponse res = new PackageDailyCountUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
