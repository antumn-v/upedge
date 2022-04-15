package com.upedge.sms.modules.center.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.sms.modules.center.entity.ServiceOrder;
import com.upedge.sms.modules.center.request.ServiceOrderListRequest;
import com.upedge.sms.modules.center.response.ServiceOrderInfoResponse;
import com.upedge.sms.modules.center.response.ServiceOrderListResponse;
import com.upedge.sms.modules.center.service.ServiceOrderService;
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
@RequestMapping("/serviceOrder")
public class ServiceOrderController {
    @Autowired
    private ServiceOrderService serviceOrderService;

    @Autowired
    RedisTemplate redisTemplate;


    /**
     * 客户订单历史
     * @param request
     * @return
     */
    @ApiOperation("客户订单历史")
    @PostMapping("/history")
    public BaseResponse customerServiceOrderHistory(@RequestBody @Valid ServiceOrderListRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        if (request.getT() == null){
            request.setT(new ServiceOrder());
        }
        request.getT().setCustomerId(session.getCustomerId());
        List<ServiceOrder> results = serviceOrderService.select(request);
        Long total = serviceOrderService.count(request);
        request.setTotal(total);
        ServiceOrderListResponse res = new ServiceOrderListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }




    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "center:serviceorder:info:id")
    public ServiceOrderInfoResponse info(@PathVariable Long id) {
        ServiceOrder result = serviceOrderService.selectByPrimaryKey(id);
        ServiceOrderInfoResponse res = new ServiceOrderInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "center:serviceorder:list")
    public ServiceOrderListResponse list(@RequestBody @Valid ServiceOrderListRequest request) {
        List<ServiceOrder> results = serviceOrderService.select(request);
        Long total = serviceOrderService.count(request);
        request.setTotal(total);
        ServiceOrderListResponse res = new ServiceOrderListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }


}
