package com.upedge.oms.modules.statistics.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.BaseCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.oms.modules.statistics.request.*;
import com.upedge.oms.modules.statistics.service.StatisticsService;
import com.upedge.oms.modules.statistics.vo.OrderStateCountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.HashMap;

@Slf4j
@Api(tags = "订单统计")
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    StatisticsService statisticsService;

    @ApiOperation("订单状态统计")
    @PostMapping("/orderStateCount")
    public BaseResponse orderStateCount(){
        Session session = UserUtil.getSession(redisTemplate);
        OrderStateCountVo orderStateCountVo = statisticsService.countOrderState(session);
        return BaseResponse.success(orderStateCountVo);
    }

//    @PostMapping("/customerOrderCost")
//    public BaseResponse customerOrderCost(){
//
//    }

    /**
     * 仪表盘 仪表盘底部处理数据统计
     * 订单导出页 订单相关数据统计
     * @return
     */
    @RequestMapping(value="/orderHandleData", method= RequestMethod.POST)
    public BaseResponse orderHandleData() {
        Session session = UserUtil.getSession(redisTemplate);
        return statisticsService.orderHandleData(session);
    }

    /**dashboardData
     * 订单支付统计
     */
    @PostMapping("/orderPayCount")
    public BaseResponse orderPayAmount(@RequestBody OrderPayCountRequest request){
        Session session = UserUtil.getSession(redisTemplate);
        if (ListUtils.isEmpty(request.getOrgIds()) && session.getUserType() == BaseCode.USER_ROLE_NORMAL){
            request.setOrgIds(session.getOrgIds());
        }
        request.setCustomerId(session.getCustomerId());
        return statisticsService.customerOrderPayCount(request);
    }
    /**
     * 客户详情页 未付款普通订单SKU统计
     */
    @RequestMapping(value = "/admin/toOrderSkuData", method= RequestMethod.POST)
    public BaseResponse toOrderSkuData(@RequestBody @Valid ToOrderSkuDataRequest request){
        return statisticsService.toOrderSkuData(request);
    }

    /**
     *客户列表页 单个客户订单数据统计
     */
    @RequestMapping(value = "/admin/customerOrderData/{customerId}",method = RequestMethod.POST)
    public BaseResponse customerOrderData(@PathVariable Long customerId){
        return statisticsService.customerOrderData(customerId);
    }

    /**
     * 仪表盘订单数据统计
     * @return
     */
    @RequestMapping(value = "/admin/dashboardOrderData",method = RequestMethod.POST)
    public BaseResponse dashboardOrderData(){
        Session session = UserUtil.getSession(redisTemplate);
        return statisticsService.dashboardOrderData(session);
    }

    /**
     * 仪表盘报表数据统计
     * @return
     */
    @RequestMapping(value = "/admin/dashboardOrderCharts",method = RequestMethod.POST)
    public BaseResponse dashboardOrderCharts(){
        Session session = UserUtil.getSession(redisTemplate);
        return statisticsService.dashboardOrderCharts(session);
    }

    /**
     * 未发货订单分析
     */
    @RequestMapping(value = "/admin/waitTrackOrderData",method = RequestMethod.POST)
    public BaseResponse waitTrackOrderData(@RequestBody @Valid WaitTrackOrderDataRequest request){
        Session session=UserUtil.getSession(redisTemplate);
        return statisticsService.waitTrackOrderData(session,request);
    }

    /**
     * 未发货订单数据详情（客户维度）
     */
    @RequestMapping(value = "/admin/waitTrackOrderDataDetails",method = RequestMethod.POST)
    public BaseResponse waitTrackOrderDataDetails(@RequestBody @Valid WaitTrackOrderDataDetailsRequest request){
        Session session=UserUtil.getSession(redisTemplate);
        return statisticsService.waitTrackOrderDataDetails(session,request);
    }

    /**
     * 客户分析  排名  listAppUserSort
     */
    @ApiOperation("客户分析  排名 listAppUserSort")
    @PostMapping("listAppUserSort")
    public BaseResponse listAppUserSort(@RequestBody AppUserSortRequest request) throws CustomerException {
     return    statisticsService.listAppUserSort(request);
    }

    /**
     * 销售统计 包裹訂單  饼图  DataCharts/distributionOrderPie
     */
    @ApiOperation("数据报表 -- 销售统计 -- 左下方饼图")
    @PostMapping("/distributionOrderPie")
    public BaseResponse distributionOrderPie(@RequestBody @Valid DistributionOrderQuery query){
        return statisticsService.distributionOrderPie(query);
    }

    /**
     * 销售统计 包裹訂單  销售利润
     */
    @ApiOperation("包裹訂單  销售利润  saleTotal")
    @PostMapping("/saleTotal")
    public BaseResponse saleTotal(@RequestBody @Valid DistributionOrderQuery query){
        return statisticsService.saleTotal(query);
    }

    @ApiOperation("包裹訂單 按月导出")
    @PostMapping("/exportSaleTotal")
    public BaseResponse exportSaleTotal(@RequestBody @Valid DistributionOrderQuery query){
        return statisticsService.exportSaleTotal(query);
    }

    /**
     * 销售统计 包裹訂單  按时间导出
     */
    @ApiOperation("包裹訂單  销售利润  按时间导出")
    @PostMapping("/exportSaleTotalByDate")
    public BaseResponse exportSaleTotalByDate(@RequestBody @Valid ExcelDostributionOrderQuery query) throws CustomerException, ParseException {
        return statisticsService.exportSaleTotalByDate(query);
    }

    /**
     * 销售统计  echarts 图  listSaleData
     */
    @ApiOperation("销售统计  echarts 图")
    @PostMapping("listSaleData")
    public BaseResponse listSaleData(@RequestBody @Valid DistributionOrderQuery query){
        HashMap<String, String> map=statisticsService.listSaleData(query);
        return  BaseResponse.success(map);
    }

    /**
     * 销售统计echarts图 右上导出当月发货包裹批发订单销售额
     * @return
     */
    @ApiOperation("销售统计echarts图 右上导出当月发货包裹批发订单销售额")
    @PostMapping(value = "exportNormalOrderSale")
    public BaseResponse exportNormalOrderSale(@RequestBody @Valid DistributionOrderQuery query) {
        return BaseResponse.success(statisticsService.exportNormalOrderSale(query));
    }

    /**
     * 销售统计echarts图 右上导出当月批发发货包裹批发订单销售额
     */
    @ApiOperation("销售统计echarts图 右上导出当月批发发货包裹批发订单销售额")
    @PostMapping(value = "exportWholesaleOrderSale")
    public BaseResponse exportWholesaleOrderSale(@RequestBody @Valid DistributionOrderQuery query) {
        return BaseResponse.success(statisticsService.exportWholesaleOrderSale(query));
    }
}
