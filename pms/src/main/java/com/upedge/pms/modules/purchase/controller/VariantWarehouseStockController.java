package com.upedge.pms.modules.purchase.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.purchase.request.VariantStockUpdateRequest;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockListRequest;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
//        List<VariantWarehouseStock> results = variantWarehouseStockService.select(request);
//        List<VariantWarehouseStockVo> variantWarehouseStockVos = new ArrayList<>();
//        for (VariantWarehouseStock result : results) {
//            VariantWarehouseStockVo variantWarehouseStockVo = new VariantWarehouseStockVo();
//            BeanUtils.copyProperties(result,variantWarehouseStockVo);
//            variantWarehouseStockVos.add(variantWarehouseStockVo);
//        }
//        Long total = variantWarehouseStockService.count(request);
//        request.setTotal(total);
//        VariantWarehouseStockListResponse res = new VariantWarehouseStockListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,variantWarehouseStockVos,request);
//        return res;
        return variantWarehouseStockService.variantWarehouseStockList(request);
    }

    @ApiOperation("修改仓库库存")
    @RequestMapping(value="/update", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantwarehousestock:update")
    public BaseResponse add(@RequestBody @Valid VariantStockUpdateRequest request) {
        Session session = UserUtil.getSession(redisTemplate);

        return variantWarehouseStockService.updateVariantStock(request,session);
    }



}