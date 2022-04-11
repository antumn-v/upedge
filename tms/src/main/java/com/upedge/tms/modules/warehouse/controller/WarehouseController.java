package com.upedge.tms.modules.warehouse.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.tms.WarehouseVo;
import com.upedge.common.utils.ListUtils;
import com.upedge.thirdparty.fpx.api.FpxCommonApi;
import com.upedge.thirdparty.fpx.request.FpxWarehouseMethodListRequest;
import com.upedge.thirdparty.fpx.vo.FpxMethodVo;
import com.upedge.tms.modules.warehouse.entity.Warehouse;
import com.upedge.tms.modules.warehouse.request.WarehouseAddRequest;
import com.upedge.tms.modules.warehouse.request.WarehouseListRequest;
import com.upedge.tms.modules.warehouse.request.WarehouseUpdateRequest;
import com.upedge.tms.modules.warehouse.response.*;
import com.upedge.tms.modules.warehouse.service.WarehouseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "仓库管理")
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;


    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "warehouse:warehouse:info:id")
    public WarehouseInfoResponse info(@PathVariable Long id) {
        Warehouse result = warehouseService.selectByPrimaryKey(id);
        WarehouseInfoResponse res = new WarehouseInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "warehouse:warehouse:list")
    public WarehouseListResponse list(@RequestBody @Valid WarehouseListRequest request) {
        if (request.getT() == null){
            request.setT(new Warehouse());
        }
        request.getT().setState(0);
        List<Warehouse> results = warehouseService.select(request);
        Long total = Long.parseLong(results.size()+"");
        request.setTotal(total);
        WarehouseListResponse res = new WarehouseListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "warehouse:warehouse:add")
    public WarehouseAddResponse add(@RequestBody @Valid WarehouseAddRequest request) {
        Warehouse entity=request.toWarehouse();
        warehouseService.insertSelective(entity);
        WarehouseAddResponse res = new WarehouseAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "warehouse:warehouse:del:id")
    public WarehouseDelResponse del(@PathVariable Long id) {
        warehouseService.deleteByPrimaryKey(id);
        WarehouseDelResponse res = new WarehouseDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "warehouse:warehouse:update")
    public WarehouseUpdateResponse update(@PathVariable Long id,@RequestBody @Valid WarehouseUpdateRequest request) {
        Warehouse entity=request.toWarehouse(id);
        warehouseService.updateByPrimaryKeySelective(entity);
        WarehouseUpdateResponse res = new WarehouseUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


    @ApiOperation("查询仓库海外仓运输方式")
    @GetMapping("/overseaMethods/{warehouseCode}")
    public BaseResponse overseaWarehouseMethods(@PathVariable String warehouseCode){
        WarehouseVo warehouseVo = (WarehouseVo) redisTemplate.opsForValue().get(RedisKey.STRING_WAREHOUSE + warehouseCode);
        if (null == warehouseVo
        || warehouseVo.getWarehouseType() == WarehouseVo.LOCAL){
            return BaseResponse.success(new ArrayList<>());
        }
        FpxWarehouseMethodListRequest fpxWarehouseMethodListRequest = new FpxWarehouseMethodListRequest();
        fpxWarehouseMethodListRequest.setSourcePositionCode(warehouseCode);
        fpxWarehouseMethodListRequest.setCategoryCode("end");
        fpxWarehouseMethodListRequest.setServiceCode("F");
        List<FpxMethodVo> fpxMethodVos = FpxCommonApi.getFpxMethods(fpxWarehouseMethodListRequest);
        if(ListUtils.isEmpty(fpxMethodVos)){
            return BaseResponse.success(new ArrayList<>());
        }
        return BaseResponse.success(fpxMethodVos);
    }


}
