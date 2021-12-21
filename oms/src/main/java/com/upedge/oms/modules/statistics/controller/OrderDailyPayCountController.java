package com.upedge.oms.modules.statistics.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.order.request.OrderDailyCountRequest;
import com.upedge.common.model.statistics.dto.OrderStatisticsDto;
import com.upedge.common.model.statistics.request.OrderStatisticsRequest;
import com.upedge.common.model.statistics.vo.ManagerOrderStatisticsVo;
import com.upedge.common.model.statistics.vo.OrderStatisticsVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.DateUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.statistics.entity.OrderDailyPayCount;
import com.upedge.oms.modules.statistics.request.OrderDailyPayCountAddRequest;
import com.upedge.oms.modules.statistics.request.OrderDailyPayCountListRequest;
import com.upedge.oms.modules.statistics.request.OrderDailyPayCountUpdateRequest;
import com.upedge.oms.modules.statistics.response.*;
import com.upedge.oms.modules.statistics.service.OrderDailyPayCountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
/**
 * 统计每日客户支付的订单，防止统计订单支付数据时全表扫描订单表
 *
 * @author guoxing
 */
@Api(tags = "订单统计")
@RestController
@RequestMapping("/orderDailyPayCount")
public class OrderDailyPayCountController {
    @Autowired
    private OrderDailyPayCountService orderDailyPayCountService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("客户下单分析")
    @PostMapping("/customer/statistics")
    public BaseResponse customerPayOrderStatistics(@RequestBody OrderStatisticsRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        OrderStatisticsDto sortDto = request.getT();
        if(null == sortDto){
            sortDto = new OrderStatisticsDto();
        }
        if(null == sortDto.getBeginTime()
        || null == sortDto.getEndTime()){
            String beginTime = DateUtils.getDate("yyyy-MM-dd", -10, Calendar.DAY_OF_MONTH);
            String endTime = DateUtils.getDate("yyyy-MM-dd",0, Calendar.MONTH);
            sortDto.setBeginTime(DateUtils.parseDate(beginTime));
            sortDto.setEndTime(DateUtils.parseDate(endTime));
        }
        sortDto.setCustomerId(session.getCustomerId());
        request.setT(sortDto);
        List<OrderStatisticsVo> orderStatisticsVos = orderDailyPayCountService.selectCustomerOrderStatisticsByDate(request);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,orderStatisticsVos);
    }

    @ApiOperation("客户经理下单分析")
    @PostMapping("/manager/statistics")
    public BaseResponse managerOrderStatistics(@RequestBody OrderStatisticsRequest request){
        OrderStatisticsDto sortDto = request.getT();
        if(null == sortDto){
            sortDto = new OrderStatisticsDto();
        }

        if(null == sortDto.getBeginTime()
                || null == sortDto.getEndTime()){
            String beginTime = DateUtils.getDate("yyyy-MM-dd", -30, Calendar.DAY_OF_MONTH);
            String endTime = DateUtils.getDate("yyyy-MM-dd",0, Calendar.MONTH);
            sortDto.setBeginTime(DateUtils.parseDate(beginTime));
            sortDto.setEndTime(DateUtils.parseDate(endTime));
        }
        request.setT(sortDto);
        List<ManagerOrderStatisticsVo> ManagerOrderStatisticsVos = orderDailyPayCountService.managerOrderStatistics(request);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,ManagerOrderStatisticsVos);
    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "statistics:orderdailypaycount:info:id")
    public OrderDailyPayCountInfoResponse info(@PathVariable Long id) {
        OrderDailyPayCount result = orderDailyPayCountService.selectByPrimaryKey(id);
        OrderDailyPayCountInfoResponse res = new OrderDailyPayCountInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "statistics:orderdailypaycount:list")
    public OrderDailyPayCountListResponse list(@RequestBody @Valid OrderDailyPayCountListRequest request) {
        List<OrderDailyPayCount> results = orderDailyPayCountService.select(request);
        Long total = orderDailyPayCountService.count(request);
        request.setTotal(total);
        OrderDailyPayCountListResponse res = new OrderDailyPayCountListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "statistics:orderdailypaycount:add")
    public OrderDailyPayCountAddResponse add(@RequestBody @Valid OrderDailyPayCountAddRequest request) {
        OrderDailyPayCount entity=request.toOrderDailyPayCount();
        orderDailyPayCountService.insertSelective(entity);
        OrderDailyPayCountAddResponse res = new OrderDailyPayCountAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/del/{id}", method=RequestMethod.POST)
    @Permission(permission = "statistics:orderdailypaycount:del:id")
    public OrderDailyPayCountDelResponse del(@PathVariable Long id) {
        orderDailyPayCountService.deleteByPrimaryKey(id);
        OrderDailyPayCountDelResponse res = new OrderDailyPayCountDelResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    @Permission(permission = "statistics:orderdailypaycount:update")
    public OrderDailyPayCountUpdateResponse update(@PathVariable Long id, @RequestBody @Valid OrderDailyPayCountUpdateRequest request) {
        OrderDailyPayCount entity=request.toOrderDailyPayCount(id);
        orderDailyPayCountService.updateByPrimaryKeySelective(entity);
        OrderDailyPayCountUpdateResponse res = new OrderDailyPayCountUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @PostMapping("selectCurrMonthSaleAmount")
    public BaseResponse selectCurrMonthSaleAmount(@RequestBody  OrderDailyCountRequest request){
      return BaseResponse.success(orderDailyPayCountService.selectCurrMonthSaleAmount(request));
    }

}
