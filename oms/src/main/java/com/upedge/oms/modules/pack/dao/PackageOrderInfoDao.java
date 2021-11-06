package com.upedge.oms.modules.pack.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.statistics.request.ManagerPackageStatisticsRequest;
import com.upedge.common.model.statistics.vo.ManagerPackageOrderCountVo;
import com.upedge.oms.modules.pack.entity.PackageOrderInfo;
import com.upedge.oms.modules.pack.request.OrderHandleTimeRequest;
import com.upedge.oms.modules.pack.request.PackageDailyCountRequest;
import com.upedge.oms.modules.pack.vo.DailyOrderHandleVo;
import com.upedge.oms.modules.pack.vo.EchartsPie;
import com.upedge.oms.modules.pack.vo.PackageCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface PackageOrderInfoDao{

    List<DailyOrderHandleVo> countDailyHandleOrder(OrderHandleTimeRequest request);

    DailyOrderHandleVo selectMaxHandleTimeAndOrderCountByDate(OrderHandleTimeRequest request);

    PackageCountVo selectPackageAndOrderCount(PackageDailyCountRequest request);
    /**
     * 一个订单拆分成多个包裹
     * @param request
     * @return
     */
    Integer countOneAppOrderSplitManyPackage(PackageDailyCountRequest request);
    /**
     * 同一包裹出现两个相同订单
     * @param request
     * @return
     */
    Integer countPackageHaveSameAppOrder(PackageDailyCountRequest request);
    /**
     * 同一个订单对应多个赛盒订单
     * @param request
     * @return
     */
    Integer countOneAppOrderToManySaiheOrder(PackageDailyCountRequest request);

    List<ManagerPackageOrderCountVo> selectManagerPackageOrderCountByDate(ManagerPackageStatisticsRequest request);

    PackageOrderInfo selectByPrimaryKey(PackageOrderInfo record);

    int deleteByPrimaryKey(PackageOrderInfo record);

    int updateByPrimaryKey(PackageOrderInfo record);

    int updateByPrimaryKeySelective(PackageOrderInfo record);

    int insert(PackageOrderInfo record);

    int insertSelective(PackageOrderInfo record);

    int insertByBatch(List<PackageOrderInfo> list);

    List<PackageOrderInfo> select(Page<PackageOrderInfo> record);

    long count(Page<PackageOrderInfo> record);

    void savePackageOrderInfo(List<PackageOrderInfo> packageOrderInfoList);

    void deletePackageOrderInfo(@Param("startDay") String startDay, @Param("endDay") String endDay,
                                @Param("updateToken") String updateToken);

    void updatePackageOrderAppUserId(@Param("startDay") String startDay,
                                     @Param("endDay") String endDay);

    void updatePackageWholesaleOrderAppUserId(@Param("startDay") String startDay,
                                              @Param("endDay") String endDay);

    List<EchartsPie> totalPackageOrderPie(@Param("startDay") String startDay,
                                          @Param("endDay") String endDay);

    //月包裹订单数
    int monthPackageOrderCount(@Param("startDay") String startDay,
                               @Param("endDay") String endDay);

    PackageOrderInfo selectPackageOrderInfoByClientOrderCode(@Param("clientOrderCode") String clientOrderCode);

}
