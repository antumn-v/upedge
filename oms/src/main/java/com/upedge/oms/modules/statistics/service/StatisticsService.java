package com.upedge.oms.modules.statistics.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.statistics.request.*;
import com.upedge.oms.modules.statistics.vo.OrderSaleVo;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

public interface StatisticsService {

    /**
     * 订单导出页 订单相关数据统计
     * @param session
     * @return
     */
    BaseResponse orderHandleData(Session session);

    /**
     * 订单支付统计
     * @param request
     * @return
     */
    BaseResponse customerOrderPayCount(OrderPayCountRequest request);

    /**
     * 未付款普通订单SKU统计
     * @return
     */
    BaseResponse toOrderSkuData(ToOrderSkuDataRequest request);

    /**
     * 单个客户订单数据统计
     * @param customerId
     * @return
     */
    BaseResponse customerOrderData(Long customerId);

    /**
     * 仪表盘订单数据统计
     * @param session
     * @return
     */
    BaseResponse dashboardOrderData(Session session);

    /**
     * 仪表盘报表数据统计
     * @param session
     * @return
     */
    BaseResponse dashboardOrderCharts(Session session);

    /**
     * 未发货订单分析
     * @param session
     * @return
     */
    BaseResponse waitTrackOrderData(Session session, WaitTrackOrderDataRequest request);

    /**
     * 未发货订单数据详情（客户维度）
     * @param session
     * @param request
     * @return
     */
    BaseResponse waitTrackOrderDataDetails(Session session, WaitTrackOrderDataDetailsRequest request);


    /**
     * 客户分析  排名  listAppUserSort
     * @param
     * @return
     */
    BaseResponse listAppUserSort(AppUserSortRequest request) throws CustomerException;

    /**
     * 销售统计  饼图
     * @param query
     * @return
     */
    BaseResponse distributionOrderPie(DistributionOrderQuery query);

    /**
     * 销售统计 数据统计
     * @param query
     * @return
     */
    BaseResponse saleTotal(DistributionOrderQuery query);

    /**
     * 销售统计 echarts
     * @param query
     * @return
     */
    HashMap<String, String> listSaleData(DistributionOrderQuery query);

    /**
     * 销售统计echarts图 右上导出当月普通订单发货包裹批发订单销售额
     */
    List<OrderSaleVo> exportNormalOrderSale(DistributionOrderQuery query);

    /**
     * 销售统计echarts图 右上导出当月批发订单发货包裹批发订单销售额
     */
    List<OrderSaleVo> exportWholesaleOrderSale(DistributionOrderQuery query);

    BaseResponse exportSaleTotalByDate(ExcelDostributionOrderQuery query) throws CustomerException, ParseException;

    BaseResponse exportSaleTotal(DistributionOrderQuery query);
}
