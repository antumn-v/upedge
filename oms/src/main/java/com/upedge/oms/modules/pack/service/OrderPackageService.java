package com.upedge.oms.modules.pack.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.pack.entity.OrderLabelPrintLog;
import com.upedge.oms.modules.pack.entity.OrderPackage;
import com.upedge.oms.modules.pack.request.*;
import com.upedge.oms.modules.pack.vo.OrderPackageInfoVo;
import com.upedge.thirdparty.shipcompany.fpx.request.OrderPackageGetLabelRequest;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderPackageService {

    List<Long> selectOrderIdsBySendTime(String sendBeginTime,String sendEndTime);

    BaseResponse packageImport(PackageInfoImportRequest request,Session session);

    List<Long> selectOrderIdsByTrackingCodes(List<String> trackingCodes);

    OrderPackage selectByScanNo(String scanNo);

    BaseResponse packReturnToPending(PackageReturnToPendingRequest request,Session session);

    List<OrderPackage> selectExStockUnUploadPackages();

    void saveAllLabelUrl();

    BaseResponse restoreRevokedPackage(Long orderId,Session session);

    BaseResponse preUploadStore(PackagePreUploadStoreRequest request,Session session);

    List<OrderPackage> selectUploadStoreFailedPackages();

    List<OrderPackage> selectByOrderIds(List<Long> orderIds);

    void packageRefreshTrackCode(OrderPackage orderPackage);

    BaseResponse packageExStock(PackageExStockRequest request, Session session);

    BaseResponse orderRevokePackage(OrderPackRevokeRequest request,Session session);

    @Transactional
    void revokePackage(Long orderId, String reason);

    List<OrderLabelPrintLog> packLabelPrintLog(Long packNo);

    BaseResponse printPackLabel(OrderPackageGetLabelRequest request, Session session);

    OrderPackageInfoVo packageInfo(OrderPackageInfoGetRequest request);

    BaseResponse createPackage(Long orderId);

    void reCreatePackage(Long orderId);

    OrderPackage selectByPrimaryKey(Long id,Long orderId,String trackingCode);

    OrderPackage selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OrderPackage record);

    int updateByPrimaryKeySelective(OrderPackage record);

    int insert(OrderPackage record);

    int insertSelective(OrderPackage record);

    List<OrderPackage> select(Page<OrderPackage> record);

    long count(Page<OrderPackage> record);
}
