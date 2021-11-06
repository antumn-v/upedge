package com.upedge.oms.modules.stock.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.stock.entity.StockAdviceSetting;
import com.upedge.oms.modules.stock.request.StockAdviceCreateOrderRequest;
import com.upedge.oms.modules.stock.request.StockAdviceSettingUpdateRequest;
import com.upedge.oms.modules.stock.response.StockAdviceSettingInfoResponse;
import com.upedge.oms.modules.stock.response.StockAdviceSettingUpdateResponse;
import com.upedge.oms.modules.stock.service.StockAdviceService;
import com.upedge.oms.modules.stock.service.StockAdviceSettingService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.validation.Valid;
import java.util.Set;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/stock/advice")
public class StockAdviceController {

    @Autowired
    StockAdviceService stockAdviceService;

    @Autowired
    private StockAdviceSettingService stockAdviceSettingService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @ApiOperation("备库建议")
    @ApiImplicitParam(name = "page",value = "分页",defaultValue = "1",required = false,paramType = "query")
    @PostMapping("/list")
    public BaseResponse stockAdviceList(@RequestParam(defaultValue = "1") Integer page,
                                        @RequestParam(defaultValue = "20") Integer pageSize){
        Session session = UserUtil.getSession(redisTemplate);
        if(null == page){
            page = 1;
        }
        Integer fromNum = (page-1)*pageSize;

        String key = RedisKey.ZSET_CUSTOMER_STOCK_ADVICE + session.getCustomerId();
        if (redisTemplate.hasKey(key)){
            Set<Object> adviceVos = redisTemplate.opsForZSet().rangeByScore(key,fromNum,fromNum + 20);
            Long count = redisTemplate.opsForZSet().size(key);
            Page p = new Page();
            p.setTotal(count);
            p.setPageNum(page);
            return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,adviceVos,p);
        }
        return stockAdviceService.customerStockAdvice(session,fromNum,pageSize);
    }

    @ApiOperation("备库建议提交订单")
    @PostMapping("/create")
    public BaseResponse stockAdviceCreateOrder(@RequestBody @Valid StockAdviceCreateOrderRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        String key = RedisKey.ZSET_CUSTOMER_STOCK_ADVICE + session.getCustomerId();
        redisTemplate.delete(key);
        return stockAdviceService.stockAdviceCreateOrder(request,session);
    }

    @ApiOperation("备库分析设置")
    @RequestMapping(value="/setting", method= RequestMethod.GET)
    @Permission(permission = "stock:stockadvicesetting:info:id")
    public StockAdviceSettingInfoResponse info() {
        Session session = UserUtil.getSession(redisTemplate);
        StockAdviceSetting result = stockAdviceSettingService.selectByCustomerId(session.getCustomerId());
        if(null == result){
            result = new StockAdviceSetting();
            result.setCustomerId(session.getCustomerId());
            stockAdviceSettingService.insert(result);
        }
        StockAdviceSettingInfoResponse res = new StockAdviceSettingInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result);
        return res;
    }

    @ApiOperation("备库分析设置修改")
    @RequestMapping(value="/setting/update", method=RequestMethod.POST)
    @Permission(permission = "stock:stockadvicesetting:update")
    public StockAdviceSettingUpdateResponse update(@RequestBody @Valid StockAdviceSettingUpdateRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        StockAdviceSetting entity=request.toStockAdviceSetting(session.getCustomerId());
        stockAdviceSettingService.updateByPrimaryKeySelective(entity);
        String key = RedisKey.ZSET_CUSTOMER_STOCK_ADVICE + session.getCustomerId();
        redisTemplate.delete(key);
        StockAdviceSettingUpdateResponse res = new StockAdviceSettingUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }
}
