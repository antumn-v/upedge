package com.upedge.oms.modules.pack.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.entity.OrderPackage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gx
 */
public interface OrderPackageDao {

    List<Long> selectOrderIdsBySendTime(@Param("sendBeginTime") String sendBeginTime, @Param("sendEndTime") String sendEndTime);

    List<Long> selectOrderIdsByTrackingCodes(@Param("trackingCodes") List<String> trackingCodes);

    List<OrderPackage> selectExStockUnUploadPackages();

    int restoreRevokedPackage(Long id);

    List<OrderPackage> selectUploadStoreFailedPackages();

    OrderPackage selectByScanNo(String scanNo);

    int revokePackageById(@Param("id") Long id, @Param("reason") String reason);

    List<OrderPackage> selectByIds(@Param("ids") List<Long> ids);

    List<OrderPackage> selectByOrderIds(@Param("orderIds") List<Long> orderIds);

    OrderPackage selectByPrimaryKey(Long id);

    OrderPackage selectByOrderId(Long orderId);

    OrderPackage selectByTrackingCode(String trackingCode);

    int deleteByPrimaryKey(OrderPackage record);

    int updateByPrimaryKey(OrderPackage record);

    int updateByPrimaryKeySelective(OrderPackage record);

    int insert(OrderPackage record);

    int insertSelective(OrderPackage record);

    int insertByBatch(List<OrderPackage> list);

    List<OrderPackage> select(Page<OrderPackage> record);

    long count(Page<OrderPackage> record);

}
