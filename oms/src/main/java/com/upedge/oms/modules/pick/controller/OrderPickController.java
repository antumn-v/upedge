package com.upedge.oms.modules.pick.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.pick.request.OrderPickCreateRequest;
import com.upedge.oms.modules.pick.request.OrderPickPreviewListRequest;
import com.upedge.oms.modules.pick.service.OrderPickService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/twicePickInfo/{id}")
    public BaseResponse twicePickInfo(@PathVariable Long id){
        return orderPickService.twicePickInfo(id);
    }

}
