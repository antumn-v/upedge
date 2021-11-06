package com.upedge.pms.modules.product.controller;


import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.pms.modules.product.request.WinningProductListRequest;
import com.upedge.pms.modules.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Winning Product")
@RestController
@RequestMapping("/winningProduct")
public class WinningProductController {


    @Autowired
    ProductService productService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("Winning Product列表")
    @PostMapping("/list")
    public BaseResponse winningProductList(@RequestBody WinningProductListRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        return productService.winningProductList(request,session);
    }
}
