package com.upedge.oms.modules.pack.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.entity.PackageDailyCount;
import com.upedge.oms.modules.pack.entity.PackageInfo;
import com.upedge.oms.modules.pack.query.PackageInfoQuery;
import com.upedge.oms.modules.pack.request.PackageInfoListRequest;
import com.upedge.oms.modules.pack.vo.PackageData;
import com.upedge.oms.modules.statistics.vo.OrderSaleVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author author
 */
public interface PackageInfoDao{

    List<PackageDailyCount> selectPackageDataByDate(@Param("beginTime") Date beginTime,
                                                    @Param("endTime") Date endTime);

    List<PackageInfo> selectPackageInfo(PackageInfoListRequest request);

    Long countPackageInfo(PackageInfoListRequest request);

    PackageInfo selectByPrimaryKey(PackageInfo record);

    int deleteByPrimaryKey(PackageInfo record);

    int updateByPrimaryKey(PackageInfo record);

    int updateByPrimaryKeySelective(PackageInfo record);

    int insert(PackageInfo record);

    int insertSelective(PackageInfo record);

    int insertByBatch(List<PackageInfo> list);

    List<PackageInfo> select(Page<PackageInfo> record);

    long count(Page<PackageInfo> record);

    //月包裹总数
    int monthPackageCount(@Param("startDay") String startDay, @Param("endDay") String endDay);

    void savePackageInfo(List<PackageInfo> packageInfoList);

    //删除不存在的包裹记录
    void deletePackageInfo(@Param("startDay") String startDay, @Param("endDay") String endDay,
                           @Param("updateToken") String updateToken);

    //日期在当前日期以内 包裹数（按天统计）
    List<PackageData> packageMonthData(@Param("startDay") String startDay,
                                       @Param("endDay") String endDay);

    List<PackageInfo> selectList(Page<PackageInfoQuery> record);

    Long selectListCount(Page<PackageInfoQuery> record);

    /**
     * 渠道当月正常订单数量
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    int countNormalOrderBySource(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    /**
     * 渠道当月补发订单数量
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    int countAgainOrderBySource(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    /**
     * 渠道当月批发订单数量
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    int countWholeSaleOrderBySource(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    /**
     *当月包裹订单总数
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    int countPackageOrderBySource(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    /**
     * 当月发货包裹批发订单销售额
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal totalNormalWholeSaleOrderAsalesBySource(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    /**
     * 当月发货包裹订单销售额
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal totalNormalOrderAsalesBySource(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    /**
     * 当月发货包裹订单采购成本
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal totalPackagePurchaseCost(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    /**
     * 当月发货包裹订单物流成本
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal totalPackageLogisticsCost(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    /**
     * 当月补发包裹采购成本
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal totalPackageAgainOrderPurchaseCost(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    /**
     * 当月补发包裹物流成本
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal totalPackageAgainOrderLogisticsCost(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);


    /**
     * 当月确认已发货订单退款金额  按照订单赛盒渠道统计
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal monthRefundTrackingYesAmountByOrderSourceId(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    /**
     * 当月确认已发货批发订单退款金额 按照订单赛盒渠道统计
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal monthWholeSaleRefundTrackingYesAmountByOrderSourceId(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    /**
     * 当月确认未发货订单退款金额 没有分配客户经理 没有渠道使用默认的128
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal monthRefundTrackingNoAmount(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    /**
     * 当月确认未发货批发订单退款金额 没有分配客户经理 没有渠道使用默认的128
     * @param startDay
     * @param endDay
     * @param orderSourceId
     * @return
     */
    BigDecimal monthWholeSaleRefundTrackingNoAmount(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    List<PackageData> packageMonthAmount(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    List<PackageData> packageMonthOrderAmount(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId, @Param("usdRate") double usdRate);

    List<PackageData> packageMonthWholeSaleOrderAmount(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId, @Param("usdRate") double usdRate);

    /**
     * 销售统计echarts图 右上导出当月普通订单发货包裹批发订单销售额
     */
    List<OrderSaleVo> listNormalOrderAsalesBySource(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);


    /**
     * 销售统计echarts图 右上导出当月批发订单发货包裹批发订单销售额
     */
    List<OrderSaleVo> exportWholesaleOrderSale(@Param("startDay") String startDay, @Param("endDay") String endDay, @Param("orderSourceId") Long orderSourceId);

    List<PackageInfo> listPackageIdNoTrackNumber(Page page);

}
