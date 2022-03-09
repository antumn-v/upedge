package com.upedge.oms.modules.rules.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.feign.TmsFeignClient;
import com.upedge.common.model.ship.request.CountriesMixShipMethodRequest;
import com.upedge.common.model.ship.vo.ShipMethodNameVo;
import com.upedge.common.model.ship.vo.ShippingTemplateRedis;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.rules.entity.OrderShipRule;
import com.upedge.oms.modules.rules.request.OrderShipRuleAddRequest;
import com.upedge.oms.modules.rules.request.OrderShipRuleUpdateRequest;
import com.upedge.oms.modules.rules.response.*;
import com.upedge.oms.modules.rules.service.OrderShipRuleService;
import com.upedge.oms.modules.rules.vo.OrderShipRuleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * 
 *
 * @author author
 */
@Api(tags = "运输规则")
@RestController
@RequestMapping("/shipRules")
public class OrderShipRuleController {
    @Autowired
    private OrderShipRuleService orderShipRulesService;

    @Autowired
    TmsFeignClient tmsFeignClient;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("运输规则详情")
    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "order:shiprules:info:id")
    public OrderShipRuleInfoResponse info(@PathVariable Long id) {
        OrderShipRuleVo result = orderShipRulesService.selectShipRuleById(id);
        OrderShipRuleInfoResponse res = new OrderShipRuleInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @ApiOperation("运输规则列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "order:shiprules:list")
    public OrderShipRuleListResponse list() {
        Session session = UserUtil.getSession(redisTemplate);
        List<OrderShipRuleVo> results = orderShipRulesService.selectCustomerShipRules(session.getCustomerId());
        for (OrderShipRuleVo result : results) {
            ShippingTemplateRedis shippingTemplateRedis = (ShippingTemplateRedis) redisTemplate.opsForHash().get(RedisKey.SHIPPING_TEMPLATE,String.valueOf(result.getShipTemplateId()));
            if (null != shippingTemplateRedis){
                result.setShipTemplateName(shippingTemplateRedis.getName());
            }
        }
        OrderShipRuleListResponse res = new OrderShipRuleListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results);
        return res;
    }

    @ApiOperation("增加运输规则")
    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "order:shiprules:add")
    public OrderShipRuleAddResponse add(@RequestBody @Valid OrderShipRuleAddRequest request) {

        OrderShipRuleVo orderShipRuleVo = orderShipRulesService.addShipRules(request);
        OrderShipRuleAddResponse res = new OrderShipRuleAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,orderShipRuleVo,request);
        return res;
    }

    @ApiOperation("删除运输规则")
    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "order:shiprules:del:id")
    public OrderShipRuleDelResponse del(@PathVariable Long id) {
        OrderShipRule entity=new OrderShipRule();
        entity.setId(id);
        entity.setState(2);
        entity.setUpdateTime(new Date());
        orderShipRulesService.updateByPrimaryKeySelective(entity);
        OrderShipRuleDelResponse res = new OrderShipRuleDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("修改运输规则")
    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "order:shiprules:update")
    public OrderShipRuleUpdateResponse update(@PathVariable Long id, @RequestBody @Valid OrderShipRuleUpdateRequest request) {
        orderShipRulesService.updateShipRules(request,id);
        OrderShipRuleUpdateResponse res = new OrderShipRuleUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("根据国家查询支持的运输方式")
    @PostMapping("/countries/shipMethods")
    public BaseResponse mixShipMethodByCountries(@RequestBody CountriesMixShipMethodRequest request){
        BaseResponse response=  tmsFeignClient.mixShipMethodNameByCountries(request);
        if (response.getCode() != ResultCode.SUCCESS_CODE
        || null == response.getData()){
            return response;
        }
        JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(response.getData()));
        List<ShipMethodNameVo> shipMethodNameVos = jsonArray.toJavaList(ShipMethodNameVo.class);
        Set<Object> methodIds = redisTemplate.opsForSet().members(RedisKey.SHIPPING_TEMPLATED_METHODS + request.getShipTemplateId());
        List<Long> ids = new ArrayList<>();
        for (Object methodId : methodIds) {
            ids.add((Long) methodId);
        }
        Iterator<ShipMethodNameVo> iterator =  shipMethodNameVos.iterator();
        while (iterator.hasNext()){
            ShipMethodNameVo shipMethodNameVo = iterator.next();;
            if (!ids.contains(shipMethodNameVo.getShipMethodId())){
                iterator.remove();
            }
        }
        return BaseResponse.success(shipMethodNameVos);
    }

}
