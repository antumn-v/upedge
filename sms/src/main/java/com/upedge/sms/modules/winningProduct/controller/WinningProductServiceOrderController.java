package com.upedge.sms.modules.winningProduct.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.sms.modules.winningProduct.entity.WinningProductServiceOrder;
import com.upedge.sms.modules.winningProduct.request.WinningProductServiceOrderAddRequest;
import com.upedge.sms.modules.winningProduct.request.WinningProductServiceOrderListRequest;
import com.upedge.sms.modules.winningProduct.request.WinningProductServiceOrderUpdateRequest;
import com.upedge.sms.modules.winningProduct.response.WinningProductServiceOrderInfoResponse;
import com.upedge.sms.modules.winningProduct.response.WinningProductServiceOrderListResponse;
import com.upedge.sms.modules.winningProduct.response.WinningProductServiceOrderUpdateResponse;
import com.upedge.sms.modules.winningProduct.service.WinningProductServiceOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "热卖品服务订单")
@RestController
@RequestMapping("/winningProductServiceOrder")
public class WinningProductServiceOrderController {
    @Autowired
    private WinningProductServiceOrderService winningProductServiceOrderService;

    @Value("${files.image.local}")
    private String localPath;
    @Value("${files.image.prefix}")
    private String imageUrlPrefix;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("订单详情")
    @RequestMapping(value="/detail/{id}", method=RequestMethod.GET)
    @Permission(permission = "winningProduct:winningproductserviceorder:info:id")
    public WinningProductServiceOrderInfoResponse info(@PathVariable Long id) {
        WinningProductServiceOrder result = winningProductServiceOrderService.selectByPrimaryKey(id);
        WinningProductServiceOrderInfoResponse res = new WinningProductServiceOrderInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "winningProduct:winningproductserviceorder:list")
    public WinningProductServiceOrderListResponse list(@RequestBody @Valid WinningProductServiceOrderListRequest request) {
        List<WinningProductServiceOrder> results = winningProductServiceOrderService.select(request);
        Long total = winningProductServiceOrderService.count(request);
        request.setTotal(total);
        WinningProductServiceOrderListResponse res = new WinningProductServiceOrderListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/create", method=RequestMethod.POST)
    @Permission(permission = "winningProduct:winningproductserviceorder:add")
    public BaseResponse add(@RequestBody @Valid WinningProductServiceOrderAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        BaseResponse response = winningProductServiceOrderService.create(request,session);
        return response;
    }


    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "winningProduct:winningproductserviceorder:update")
    public WinningProductServiceOrderUpdateResponse update(@PathVariable Long id,@RequestBody @Valid WinningProductServiceOrderUpdateRequest request) {
        WinningProductServiceOrder entity=request.toWinningProductServiceOrder(id);
        winningProductServiceOrderService.updateByPrimaryKeySelective(entity);
        WinningProductServiceOrderUpdateResponse res = new WinningProductServiceOrderUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @ApiOperation("上传产品文件")
    @PostMapping("/uploadProductFile/{id}")
    public BaseResponse uploadProductFile(@PathVariable Long id, MultipartFile file){
        WinningProductServiceOrder winningProductServiceOrder = winningProductServiceOrderService.selectByPrimaryKey(id);
        if (null == winningProductServiceOrder){
            return BaseResponse.failed("订单不存在");
        }
        if (file == null
                || file.isEmpty()){
            return BaseResponse.failed("空文件");
        }
        String fileName=file.getOriginalFilename();
        //文件上传
        try {
            file.transferTo(new File(localPath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = imageUrlPrefix + fileName;
        winningProductServiceOrder = new WinningProductServiceOrder();
        winningProductServiceOrder.setProductFileLink(path);
        winningProductServiceOrder.setId(id);
        winningProductServiceOrder.setUpdateTime(new Date());
        winningProductServiceOrderService.updateByPrimaryKeySelective(winningProductServiceOrder);
        return BaseResponse.success();
    }


}
