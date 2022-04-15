package com.upedge.sms.modules.overseaWarehouse.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.sms.modules.overseaWarehouse.entity.OverseaWarehouseServiceOrderItem;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderItemAddRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderItemListRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderItemUpdateRequest;
import com.upedge.sms.modules.overseaWarehouse.request.OverseaWarehouseServiceOrderItemUploadFpxRequest;
import com.upedge.sms.modules.overseaWarehouse.response.*;
import com.upedge.sms.modules.overseaWarehouse.service.OverseaWarehouseServiceOrderItemService;
import com.upedge.thirdparty.fpx.api.FpxCommonApi;
import com.upedge.thirdparty.fpx.vo.FpxMeasureUnit;
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
@RestController
@RequestMapping("/overseaWarehouseServiceOrderItem")
public class OverseaWarehouseServiceOrderItemController {
    @Autowired
    private OverseaWarehouseServiceOrderItemService overseaWarehouseServiceOrderItemService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("产品上传fpx")
    @PostMapping("/uploadFpx")
    public BaseResponse skuUploadFpx(@RequestBody@Valid OverseaWarehouseServiceOrderItemUploadFpxRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return overseaWarehouseServiceOrderItemService.uploadFpx(request,session);
    }

    @ApiOperation("获取FPX计量单位")
    @GetMapping("/listMeasureUnit")
    public BaseResponse listMeasureUnit(){
        List<FpxMeasureUnit> fpxMeasureUnits = FpxCommonApi.selectMeasureUnit();
        return BaseResponse.success(fpxMeasureUnits);
    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderitem:info:id")
    public OverseaWarehouseServiceOrderItemInfoResponse info(@PathVariable Long id) {
        OverseaWarehouseServiceOrderItem result = overseaWarehouseServiceOrderItemService.selectByPrimaryKey(id);
        OverseaWarehouseServiceOrderItemInfoResponse res = new OverseaWarehouseServiceOrderItemInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderitem:list")
    public OverseaWarehouseServiceOrderItemListResponse list(@RequestBody @Valid OverseaWarehouseServiceOrderItemListRequest request) {
        List<OverseaWarehouseServiceOrderItem> results = overseaWarehouseServiceOrderItemService.select(request);
        Long total = overseaWarehouseServiceOrderItemService.count(request);
        request.setTotal(total);
        OverseaWarehouseServiceOrderItemListResponse res = new OverseaWarehouseServiceOrderItemListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderitem:add")
    public OverseaWarehouseServiceOrderItemAddResponse add(@RequestBody @Valid OverseaWarehouseServiceOrderItemAddRequest request) {
        OverseaWarehouseServiceOrderItem entity=request.toOverseaWarehouseServiceOrderItem();
        overseaWarehouseServiceOrderItemService.insertSelective(entity);
        OverseaWarehouseServiceOrderItemAddResponse res = new OverseaWarehouseServiceOrderItemAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderitem:del:id")
    public OverseaWarehouseServiceOrderItemDelResponse del(@PathVariable Long id) {
        overseaWarehouseServiceOrderItemService.deleteByPrimaryKey(id);
        OverseaWarehouseServiceOrderItemDelResponse res = new OverseaWarehouseServiceOrderItemDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "overseaWarehouse:overseawarehouseserviceorderitem:update")
    public OverseaWarehouseServiceOrderItemUpdateResponse update(@PathVariable Long id,@RequestBody @Valid OverseaWarehouseServiceOrderItemUpdateRequest request) {
        OverseaWarehouseServiceOrderItem entity=request.toOverseaWarehouseServiceOrderItem(id);
        overseaWarehouseServiceOrderItemService.updateByPrimaryKeySelective(entity);
        OverseaWarehouseServiceOrderItemUpdateResponse res = new OverseaWarehouseServiceOrderItemUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
