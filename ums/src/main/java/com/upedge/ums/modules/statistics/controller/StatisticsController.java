package com.upedge.ums.modules.statistics.controller;

import com.alibaba.fastjson.JSONArray;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.OmsFeignClient;
import com.upedge.common.model.statistics.request.OrderStatisticsRequest;
import com.upedge.common.model.statistics.vo.CustomerOrderStatisticsVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.statistics.service.StatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@Api(tags = "先不对接")
//@RestController
//@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    OmsFeignClient omsFeignClient;

    @PostMapping("/customer/order/sort")
    public BaseResponse customerOrderStatistics(@RequestBody OrderStatisticsRequest request){
        BaseResponse OrderStatisticsResponse = omsFeignClient.customerPayOrderStatistics(request);
        if(OrderStatisticsResponse.getCode() == ResultCode.SUCCESS_CODE
        && OrderStatisticsResponse.getData() != null){
            List<CustomerOrderStatisticsVo> CustomerOrderStatisticsVos
                    = JSONArray.parseArray(OrderStatisticsResponse.getData().toString()).toJavaList(CustomerOrderStatisticsVo.class);
            List<String> managerCodes = new ArrayList<>();
            List<Long> customerIds = new ArrayList<>();
            CustomerOrderStatisticsVos.forEach(CustomerOrderStatisticsVo -> {
                managerCodes.add(CustomerOrderStatisticsVo.getManagerCode());
                customerIds.add(CustomerOrderStatisticsVo.getCustomerId());
            });
        }
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,new ArrayList<>());
    }



    /**
     * 仪表盘数据统计
     * @return
     */
    @RequestMapping(value = "/admin/dashboardCustomerData",method = RequestMethod.POST)
    public BaseResponse dashboardCustomerData(){
        Session session = UserUtil.getSession(redisTemplate);
        return statisticsService.dashboardCustomerData(session);
    }

    /**
     * 仪表盘财务数据
     */
    @RequestMapping(value= "/admin/dashboardFinanceData",method = RequestMethod.POST)
    public BaseResponse dashboardFinanceData(){
        Session session=UserUtil.getSession(redisTemplate);
        return statisticsService.dashboardFinanceData(session);
    }

    /**
     * 客户分析 新店铺 新客户 数据
     */
    @ApiOperation("客户分析 新店铺 新客户 数据")
    @RequestMapping(value = "/admin/customerChartsData",method = RequestMethod.POST)
    public BaseResponse customerChartsData(){
        Session session=UserUtil.getSession(redisTemplate);
        return statisticsService.customerChartsData(session);
    }


}
