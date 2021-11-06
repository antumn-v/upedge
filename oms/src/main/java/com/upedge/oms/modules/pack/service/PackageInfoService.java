package com.upedge.oms.modules.pack.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.request.*;
import com.upedge.oms.modules.pack.response.PackageInfoListQueryResponse;
import com.upedge.oms.modules.pack.response.PackageInfoListResponse;
import com.upedge.oms.modules.pack.vo.PackageCountVo;
import com.upedge.oms.modules.pack.vo.PackageData;
import com.upedge.oms.modules.statistics.vo.OrderSaleVo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface PackageInfoService{

    public static final double USD_RATE=6.3;

    PackageCountVo selectPackageCount(PackageDailyCountRequest request);

    int updatePackageInfoByDate(PackageInfoUpdateRequest request);

    BaseResponse packageCharts(PackageInfoQueryRequest request);

    PackageInfoListResponse adminList(PackageInfoListRequest request);

    PackageInfoListQueryResponse adminListV2(PackageInfoListQueryRequest request);

    /**
     * 渠道当月正常订单数量
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    int countNormalOrderBySource(String startDay, String endDay, Long orderSourceId);

    /**
     * 渠道当月补发订单数量
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    int countAgainOrderBySource(String startDay, String endDay, Long orderSourceId);

    /**
     * 渠道当月批发订单数量
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    int countWholeSaleOrderBySource(String startDay, String endDay, Long orderSourceId);

    /**
     * 渠道当月包裹订单总数
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    int countPackageOrderBySource(String startDay, String endDay, Long orderSourceId);

    /**
     * 当月发货包裹批发订单销售额
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal totalNormalWholeSaleOrderAsalesBySource(String startDay, String endDay, Long orderSourceId);

    /**
     * 当月发货包裹订单销售额
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal totalNormalOrderAsalesBySource(String startDay, String endDay, Long orderSourceId);

    /**
     * 当月发货包裹订单采购成本
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal totalPackagePurchaseCost(String startDay, String endDay, Long orderSourceId);

    /**
     * 当月发货包裹订单物流成本
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal totalPackageLogisticsCost(String startDay, String endDay, Long orderSourceId);

    /**
     * 当月补发包裹采购成本
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal totalPackageAgainOrderPurchaseCost(String startDay, String endDay, Long orderSourceId);

    /**
     * 当月补发包裹物流成本
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal totalPackageAgainOrderLogisticsCost(String startDay, String endDay, Long orderSourceId);


    /**
     * 当月确认已发货订单退款金额 按照赛盒渠道统计
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal monthRefundTrackingYesAmountByOrderSourceId(String startDay, String endDay, Long orderSourceId);

    /**
     * 当月确认已发货批发订单退款金额 按照赛盒渠道统计
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal monthWholeSaleRefundTrackingYesAmountByOrderSourceId(String startDay, String endDay, Long orderSourceId);

    /**
     * 当月确认未发货订单退款金额
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal monthRefundTrackingNoAmount(String startDay, String endDay, Long orderSourceId);

    /**
     * 当月确认未发货批发订单退款金额
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal monthWholeSaleRefundTrackingNoAmount(String startDay, String endDay, Long orderSourceId);


    /**
     * 日期在当前日期以内  获取包裹支出费用
     */
    List<PackageData> packageMonthAmount(String startDay, String endDay, Long orderSourceId);
    List<PackageData> packageMonthOrderAmount(String startDay, String endDay, Long orderSourceId, double usdRate);
    List<PackageData> packageMonthWholeSaleOrderAmount(String startDay, String endDay, Long orderSourceId, double usdRate);

    /**
     * 销售统计echarts图 右上导出当月普通订单发货包裹批发订单销售额
     */
    List<OrderSaleVo> listNormalOrderAsalesBySource(String startDay, String endDay, Long orderSourceId);

    /**
     * 销售统计echarts图 右上导出当月批发订单发货包裹批发订单销售额
     */
    List<OrderSaleVo> exportWholesaleOrderSale(String startDay, String endDay, Long orderSourceId);

    void checkPackageIdNoTrackNumber(Page page);
}

