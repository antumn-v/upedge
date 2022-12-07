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
import com.upedge.pms.modules.purchase.response.PurchasePlanUpdateResponse;
import com.upedge.pms.modules.purchase.service.PurchasePlanService;
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
    public BaseResponse list(@RequestBody @Valid PurchasePlanListRequest request) {
        return purchasePlanService.selectPurchasePlanList();
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
