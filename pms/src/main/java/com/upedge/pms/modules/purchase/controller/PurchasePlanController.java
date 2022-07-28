package com.upedge.pms.modules.purchase.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.purchase.entity.PurchasePlan;
import com.upedge.pms.modules.purchase.request.PurchasePlanAddRequest;
import com.upedge.pms.modules.purchase.request.PurchasePlanListRequest;
import com.upedge.pms.modules.purchase.request.PurchasePlanUpdateRequest;
import com.upedge.pms.modules.purchase.response.PurchasePlanListResponse;
import com.upedge.pms.modules.purchase.response.PurchasePlanUpdateResponse;
import com.upedge.pms.modules.purchase.service.PurchasePlanService;
import com.upedge.pms.modules.purchase.vo.PurchasePlanVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "采购计划")
@RestController
@RequestMapping("/purchasePlan")
public class PurchasePlanController {
    @Autowired
    private PurchasePlanService purchasePlanService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseplan:list")
    public PurchasePlanListResponse list(@RequestBody @Valid PurchasePlanListRequest request) {
        if (request.getT() == null){
            request.setT(new PurchasePlan());
        }
        request.getT().setState(0);
        request.setPageSize(-1);
        request.initFromNum();
        List<PurchasePlan> results = purchasePlanService.select(request);
        Map<String,List<PurchasePlan>> map = new HashMap<>();
        for (PurchasePlan result : results) {
            if (!map.containsKey(result.getSupplierName())){
                map.put(result.getSupplierName(), new ArrayList<>());
            }
            map.get(result.getSupplierName()).add(result);
        }
        List<PurchasePlanVo> purchasePlanVos = new ArrayList<>();
        map.forEach((s, purchasePlans) -> {
            PurchasePlanVo purchasePlanVo = new PurchasePlanVo();
            purchasePlanVo.setSupplierName(s);
            purchasePlanVo.setPurchasePlans(purchasePlans);
            purchasePlanVos.add(purchasePlanVo);
        });
        PurchasePlanListResponse res = new PurchasePlanListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,purchasePlanVos,request);
        return res;
    }

    @ApiOperation("添加采购计划")
    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseplan:add")
    public BaseResponse add(@RequestBody @Valid PurchasePlanAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        return purchasePlanService.addPurchasePlan(request,session);
    }


    @ApiOperation("修改采购计划")
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "purchase:purchaseplan:update")
    public PurchasePlanUpdateResponse update(@PathVariable Integer id,@RequestBody @Valid PurchasePlanUpdateRequest request) {
        PurchasePlan entity=request.toPurchasePlan(id);
        purchasePlanService.updateByPrimaryKeySelective(entity);
        PurchasePlanUpdateResponse res = new PurchasePlanUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("删除采购计划")
    @PostMapping("/delete/{id}")
    public BaseResponse delete(@PathVariable Integer id){
        purchasePlanService.deleteByPrimaryKey(id);
        return BaseResponse.success();
    }

    @ApiOperation("修改采购信息")
    @PostMapping("/updatePurchaseInfo")
    public BaseResponse updatePurchaseInfo(@RequestBody PurchasePlanUpdateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return purchasePlanService.updatePurchaseSku(request,session);
    }

}
