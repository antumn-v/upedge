package com.upedge.oms.modules.vat.controller;

import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.vat.entity.VatRuleItem;
import com.upedge.oms.modules.vat.request.VatRuleItemListRequest;
import com.upedge.oms.modules.vat.request.VatRuleItemRemoveRequest;
import com.upedge.oms.modules.vat.request.VatRuleItemUpdateRequest;
import com.upedge.oms.modules.vat.response.*;
import com.upedge.oms.modules.vat.service.VatRuleItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/vatRuleItem")
public class VatRuleItemController {
    @Autowired
    private VatRuleItemService vatRuleItemService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    //======================admin============================

    /**
     * VAT规则地区列表
     * @param ruleId
     * @return
     */
    @RequestMapping(value="/admin/list/{ruleId}", method=RequestMethod.POST)
    public VatRuleItemListResponse list(@PathVariable Long ruleId, @RequestBody VatRuleItemListRequest request) {
        if (request.getT() == null){
            VatRuleItem vatRuleItem=new VatRuleItem();
            vatRuleItem.setRuleId(ruleId);
            request.setT(vatRuleItem);
        }else {
            request.getT().setRuleId(ruleId);
        }

        List<VatRuleItem> results = vatRuleItemService.select(request);
        Long total = vatRuleItemService.count(request);
        request.setTotal(total);
        VatRuleItemListResponse res = new VatRuleItemListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    /**
     * 更新规则配置项
     * @param request
     * @return
     */
    @RequestMapping(value="/admin/updateRuleItems", method=RequestMethod.POST)
    public VatRuleItemUpdateResponse updateRuleItems(@RequestBody @Valid VatRuleItemUpdateRequest request) {
        Session session= UserUtil.getSession(redisTemplate);
        return vatRuleItemService.updateRuleItems(request,session);
    }

    /**
     * 移除地区配置
     * @return
     */
    @RequestMapping(value="/admin/remove", method=RequestMethod.POST)
    public VatRuleItemRemoveResponse removeItem(@RequestBody @Valid VatRuleItemRemoveRequest request) {
        Session session= UserUtil.getSession(redisTemplate);
        return vatRuleItemService.removeItem(request,session);
    }

    /**
     * 已配置的规则地区列表
     * @param ruleId
     * @return
     */
    @RequestMapping(value="/admin/areaSelect/{ruleId}", method=RequestMethod.POST)
    public VatRuleItemListResponse areaSelect(@PathVariable Long ruleId) {
        return vatRuleItemService.areaSelect(ruleId);
    }

}
