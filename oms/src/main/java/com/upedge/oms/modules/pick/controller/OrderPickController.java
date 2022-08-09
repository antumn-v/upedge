package com.upedge.oms.modules.pick.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.pick.request.OrderPickCreateRequest;
import com.upedge.oms.modules.pick.request.OrderPickPreviewListRequest;
import com.upedge.oms.modules.pick.request.TwicePickSubmitRequest;
import com.upedge.oms.modules.pick.service.OrderPickService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 订单拣货
 *
 * @author gx
 */
@Api(tags = "订单分拣")
@RestController
@RequestMapping("/orderPick")
public class OrderPickController {
    @Autowired
    private OrderPickService orderPickService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("波次处理列表")
    @PostMapping("/previewList")
    public BaseResponse previewList(@RequestBody OrderPickPreviewListRequest request){
        return orderPickService.previewList(request);
    }

    @ApiOperation("创建波次")
    @PostMapping("/create")
    public BaseResponse create(@RequestBody OrderPickCreateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return orderPickService.create(request,session);
    }

    @ApiOperation("二次分拣")
    @PostMapping("/twicePickInfo/{id}")
    public BaseResponse twicePickInfo(@PathVariable Long id){
        return orderPickService.twicePickInfo(id);
    }

    @ApiOperation("二次分拣提交")
    @PostMapping("/twicePickSubmit")
    public BaseResponse twicePickSubmit(@RequestBody@Valid TwicePickSubmitRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return orderPickService.twicePickSubmit(request,session);
    }

    @ApiOperation("打印拣货单")
    @PostMapping("/print/{id}")
    public BaseResponse printPickList(@PathVariable Long id){
        Session session = UserUtil.getSession(redisTemplate);
        return orderPickService.printPickInfo(id,session);
    }
}
