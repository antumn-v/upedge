package com.upedge.pms.modules.purchase.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStockRecord;
import com.upedge.pms.modules.purchase.request.VariantStockExImRecordUpdateRequest;
import com.upedge.pms.modules.purchase.request.VariantStockUpdateRequest;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockListRequest;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "变体仓库库存管理")
@RestController
@RequestMapping("/variantWarehouseStock")
public class VariantWarehouseStockController {
    @Autowired
    private VariantWarehouseStockService variantWarehouseStockService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("仓库清单")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantwarehousestock:list")
    public BaseResponse list(@RequestBody @Valid VariantWarehouseStockListRequest request) {
        return variantWarehouseStockService.variantWarehouseStockList(request);
    }

    @ApiOperation("修改仓库库存")
    @RequestMapping(value="/update", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantwarehousestock:update")
    public BaseResponse add(@RequestBody @Valid VariantStockUpdateRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return variantWarehouseStockService.updateVariantStock(request,session);
    }

    @ApiOperation("手动出库")
    @PostMapping("/customEx")
    public BaseResponse stockEx(@RequestBody@Valid VariantStockExImRecordUpdateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        request.setProcessType(VariantWarehouseStockRecord.CUSTOM_EX);
        request.setRelateId(IdGenerate.nextId());
        return variantWarehouseStockService.variantStockEx(request,session);
    }

    @ApiOperation("手动入库")
    @PostMapping("/customIm")
    public BaseResponse stockIm(@RequestBody@Valid VariantStockExImRecordUpdateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        request.setProcessType(VariantWarehouseStockRecord.CUSTOM_IM);
        request.setRelateId(IdGenerate.nextId());
        return variantWarehouseStockService.variantStockIm(request,session);
    }



}
