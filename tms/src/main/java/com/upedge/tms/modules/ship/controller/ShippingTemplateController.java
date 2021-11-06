package com.upedge.tms.modules.ship.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.PmsFeignClient;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.tms.modules.ship.entity.ShippingTemplate;
import com.upedge.tms.modules.ship.request.ShippingTemplateAddRequest;
import com.upedge.tms.modules.ship.request.ShippingTemplateListRequest;
import com.upedge.tms.modules.ship.request.ShippingTemplateUpdateRequest;
import com.upedge.tms.modules.ship.response.*;
import com.upedge.tms.modules.ship.service.ShippingTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 运输模板表
 *
 * @author author
 */
@RestController
@RequestMapping("/shippingTemplate")
public class ShippingTemplateController {
    @Autowired
    private ShippingTemplateService shippingTemplateService;
    @Autowired
    private PmsFeignClient pmsFeignClient;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value="/all", method=RequestMethod.POST)
    public ShippingTemplateListResponse allShippingTemplate() {
        return shippingTemplateService.allShippingTemplate();
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    public ShippingTemplateListResponse list(@RequestBody @Valid ShippingTemplateListRequest request) {
        return shippingTemplateService.list(request);
    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    public ShippingTemplateInfoResponse info(@PathVariable Long id) {
        ShippingTemplate result = shippingTemplateService.selectByPrimaryKey(id);
        ShippingTemplateInfoResponse res = new ShippingTemplateInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public ShippingTemplateAddResponse add(@RequestBody @Valid ShippingTemplateAddRequest request) {
        ShippingTemplate entity=request.toShippingTemplate();
        shippingTemplateService.insertSelective(entity);
        ShippingTemplateAddResponse res = new ShippingTemplateAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    public ShippingTemplateUpdateResponse update(@PathVariable Long id, @RequestBody @Valid ShippingTemplateUpdateRequest request) {
        ShippingTemplate entity=request.toShippingTemplate(id);
        shippingTemplateService.updateByPrimaryKeySelective(entity);
        ShippingTemplateUpdateResponse res = new ShippingTemplateUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    /**
     * 启用运输模板
     * @return
     */
    @RequestMapping(value="/enable/{id}", method=RequestMethod.POST)
    public ShippingTemplateEnableResponse enableShippingTemplate(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return shippingTemplateService.enableShippingTemplate(id);
    }

    /**
     * 禁用运输模板
     * @return
     */
    @RequestMapping(value="/disable/{id}", method=RequestMethod.POST)
    public ShippingTemplateDisableResponse disableShippingTemplate(@PathVariable Long id) {
        Session session = UserUtil.getSession(redisTemplate);
        return shippingTemplateService.disableShippingTemplate(id);
    }

    /**
     * 运输模板下的产品列表
     * @param shippingId
     * @return
     */
    @RequestMapping(value="/{shippingId}/listProduct", method=RequestMethod.POST)
    public BaseResponse listProduct(@PathVariable Long shippingId) {
         BaseResponse response=pmsFeignClient.listProductByShippingId(shippingId);
         return response;
    }


}
