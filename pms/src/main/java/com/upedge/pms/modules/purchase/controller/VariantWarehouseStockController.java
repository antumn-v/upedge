package com.upedge.pms.modules.purchase.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.oms.order.OrderItemQuantityVo;
import com.upedge.common.model.pms.request.VariantStockRestoreLockQuantityRequest;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.web.util.RedisUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStockRecord;
import com.upedge.pms.modules.purchase.request.VariantStockExImRecordUpdateRequest;
import com.upedge.pms.modules.purchase.request.VariantStockListRequest;
import com.upedge.pms.modules.purchase.request.VariantStockUpdateRequest;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockDeleteRequest;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    public BaseResponse list(@RequestBody @Valid VariantStockListRequest request) {
        return variantWarehouseStockService.variantStockList(request);
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

    @ApiOperation("包裹出库")
    @PostMapping("/packageEx")
    public BaseResponse packageEx(@RequestBody OrderItemQuantityVo orderItemQuantityVo) {
        try {
            return variantWarehouseStockService.packageShipped(orderItemQuantityVo);
        } catch (Exception e) {
            return BaseResponse.failed(e.getMessage());
        }
    }

    @ApiOperation("手动入库")
    @PostMapping("/customIm")
    public BaseResponse stockIm(@RequestBody@Valid VariantStockExImRecordUpdateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        request.setProcessType(VariantWarehouseStockRecord.CUSTOM_IM);
        request.setRelateId(IdGenerate.nextId());
        return variantWarehouseStockService.variantStockIm(request,session);
    }

    @ApiOperation("修改安全库存")
    @PostMapping("/updateSafeQuantity")
    public BaseResponse updateSafeQuantity(@RequestBody @Valid VariantStockUpdateRequest request){
        VariantWarehouseStock variantWarehouseStock = new VariantWarehouseStock();
        variantWarehouseStock.setVariantId(request.getVariantId());
        variantWarehouseStock.setWarehouseCode(request.getWarehouseCode());
        variantWarehouseStock.setSafeStock(request.getStock());
        variantWarehouseStockService.updateByPrimaryKeySelective(variantWarehouseStock);
        return BaseResponse.success();
    }


    @PostMapping("/redisKeyTest")
    public BaseResponse redisKeyTest(@RequestBody List<Long> variantIds){
        for (Long variantId : variantIds) {
            String key = RedisKey.KEY_VARIANT_WAREHOUSE_STOCK_LOCK + "CNHZ:"  + variantId;
            boolean b = RedisUtil.lock(redisTemplate,key,10L,10 * 1000L);
            log.warn("{}---> 加锁结果: {}" ,variantId,b);
        }
        return BaseResponse.success();
    }

    @ApiOperation("删除库存")
    @PostMapping("/delete")
    public BaseResponse deleteVariantStock(@RequestBody@Valid VariantWarehouseStockDeleteRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return variantWarehouseStockService.deleteVariantStock(request,session);
    }

    @PostMapping("/orderCancelShip")
    public int orderCancelShip(@RequestBody OrderItemQuantityVo orderItemQuantityVo){
        try {
            int i = variantWarehouseStockService.orderCancelShip(orderItemQuantityVo);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @PostMapping("/restoreLockQuantity")
    public BaseResponse restoreLockQuantity(@RequestBody VariantStockRestoreLockQuantityRequest request){
        int i = variantWarehouseStockService.restoreStockByLockStock(request.getVariantId(), request.getWarehouseStock(), request.getLockQuantity());
        if (i == 1){
            return BaseResponse.success();
        }
        return BaseResponse.failed();
    }


    @PostMapping("/orderCheckStock")
    public BaseResponse orderCheckStock(@RequestBody List<OrderItemQuantityVo> orderItemQuantityVos){
        for (OrderItemQuantityVo orderItemQuantityVo : orderItemQuantityVos) {
            try {
                boolean b = variantWarehouseStockService.orderCheckStock(orderItemQuantityVo);
                log.warn("orderID:{},结果：{}",orderItemQuantityVo.getOrderId(),b);
            } catch (Exception e) {
                return BaseResponse.failed(e.getMessage());
            }
        }
        return BaseResponse.success();
    }



}
