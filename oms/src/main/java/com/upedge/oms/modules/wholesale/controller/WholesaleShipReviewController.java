package com.upedge.oms.modules.wholesale.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.wholesale.request.WholesaleOrderShipUpdateRequest;
import com.upedge.oms.modules.wholesale.service.WholesaleOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Api(tags = "批发订单运费审核")
@RestController
@RequestMapping("/wholesaleShipReview")
public class WholesaleShipReviewController {

    @Autowired
    WholesaleOrderService wholesaleOrderService;

    @Autowired
    RedisTemplate redisTemplate;


    @ApiOperation("修改运费信息")
    @PostMapping("/{id}/update")
    public BaseResponse updateShipReview(@PathVariable Long id, @RequestBody WholesaleOrderShipUpdateRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return wholesaleOrderService.updateShip(request,session);
    }


    @PostMapping("/{id}/reject")
    public BaseResponse rejectShipReview(@PathVariable Long id){
        return BaseResponse.success();
    }
}
