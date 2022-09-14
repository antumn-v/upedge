package com.upedge.oms.modules.pick.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.pick.entity.OrderPick;
import com.upedge.oms.modules.pick.request.OrderPickCreateRequest;
import com.upedge.oms.modules.pick.request.OrderPickListRequest;
import com.upedge.oms.modules.pick.request.OrderPickPreviewListRequest;
import com.upedge.oms.modules.pick.request.TwicePickSubmitRequest;
import com.upedge.oms.modules.pick.service.OrderPickService;
import com.upedge.oms.modules.pick.vo.OrderPickWaveInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @ApiOperation("波次详情")
    @PostMapping("/waveInfo/{waveNo}")
    public BaseResponse waveInfo(@PathVariable Integer waveNo){
        OrderPickWaveInfoVo orderPickWaveInfoVo = orderPickService.wavePickInfo(waveNo);
        if (null == orderPickWaveInfoVo){
            return BaseResponse.failed("波次不存在");
        }
        return BaseResponse.success(orderPickWaveInfoVo);
    }

    @ApiOperation("波次列表")
    @PostMapping("/waveList")
    public BaseResponse wareList(@RequestBody OrderPickListRequest request){
        List<OrderPick> orderPicks = orderPickService.select(request);
        Long count = orderPickService.count(request);

        request.setTotal(count);
        return BaseResponse.success(orderPicks,request);
    }

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
    @PostMapping("/twicePickInfo/{waveNo}")
    public BaseResponse twicePickInfo(@PathVariable Integer waveNo){
        return orderPickService.twicePickInfo(waveNo);
    }

    @ApiOperation("二次分拣提交")
    @PostMapping("/twicePickSubmit")
    public BaseResponse twicePickSubmit(@RequestBody@Valid TwicePickSubmitRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return orderPickService.twicePickSubmit(request,session);
    }

    @ApiOperation("打印拣货单")
    @PostMapping("/print/{waveNo}")
    public BaseResponse printPickList(@PathVariable Integer waveNo){
        Session session = UserUtil.getSession(redisTemplate);
        return orderPickService.printPickInfo(waveNo,session);
    }

    @ApiOperation("已打印")
    @PostMapping("/printClick/{id}")
    public BaseResponse printClick(@PathVariable Long id){
        OrderPick orderPick = new OrderPick();
        orderPick.setId(id);
        orderPick.setIsPrinted(true);
        orderPickService.updateByPrimaryKeySelective(orderPick);
        redisTemplate.opsForHash().put(RedisKey.HASH_ORDER_PICK_WAVE_PRINTED,id.toString(),1);
        return BaseResponse.success();
    }


}
