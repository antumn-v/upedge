package com.upedge.pms.modules.purchase.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.vo.VariantWarehouseStockVo;
import com.upedge.pms.modules.purchase.entity.VariantWarehouseStock;
import com.upedge.pms.modules.purchase.request.VariantStockUpdateRequest;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockListRequest;
import com.upedge.pms.modules.purchase.request.VariantWarehouseStockUpdateRequest;
import com.upedge.pms.modules.purchase.response.VariantWarehouseStockDelResponse;
import com.upedge.pms.modules.purchase.response.VariantWarehouseStockListResponse;
import com.upedge.pms.modules.purchase.response.VariantWarehouseStockUpdateResponse;
import com.upedge.pms.modules.purchase.service.VariantWarehouseStockService;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
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
@Api(tags = "变体仓库库存管理")
@RestController
@RequestMapping("/variantWarehouseStock")
public class VariantWarehouseStockController {
    @Autowired
    private VariantWarehouseStockService variantWarehouseStockService;

    @Autowired
    RedisTemplate redisTemplate;


    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantwarehousestock:list")
    public VariantWarehouseStockListResponse list(@RequestBody @Valid VariantWarehouseStockListRequest request) {
        List<VariantWarehouseStock> results = variantWarehouseStockService.select(request);
        List<VariantWarehouseStockVo> variantWarehouseStockVos = new ArrayList<>();
        for (VariantWarehouseStock result : results) {
            VariantWarehouseStockVo variantWarehouseStockVo = new VariantWarehouseStockVo();
            BeanUtils.copyProperties(result,variantWarehouseStockVo);
            variantWarehouseStockVos.add(variantWarehouseStockVo);
        }
        Long total = variantWarehouseStockService.count(request);
        request.setTotal(total);
        VariantWarehouseStockListResponse res = new VariantWarehouseStockListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,variantWarehouseStockVos,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantwarehousestock:add")
    public BaseResponse add(@RequestBody @Valid VariantStockUpdateRequest request) {
        Session session = UserUtil.getSession(redisTemplate);

        return variantWarehouseStockService.updateVariantStock(request,session);
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantwarehousestock:del:id")
    public VariantWarehouseStockDelResponse del(@PathVariable Long id) {
        variantWarehouseStockService.deleteByPrimaryKey(id);
        VariantWarehouseStockDelResponse res = new VariantWarehouseStockDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:variantwarehousestock:update")
    public VariantWarehouseStockUpdateResponse update(@PathVariable Long id,@RequestBody @Valid VariantWarehouseStockUpdateRequest request) {
        VariantWarehouseStock entity=request.toVariantWarehouseStock(id);
        variantWarehouseStockService.updateByPrimaryKeySelective(entity);
        VariantWarehouseStockUpdateResponse res = new VariantWarehouseStockUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }


}
