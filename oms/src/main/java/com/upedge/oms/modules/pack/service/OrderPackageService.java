package com.upedge.oms.modules.pack.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.pack.entity.OrderLabelPrintLog;
import com.upedge.oms.modules.pack.entity.OrderPackage;
import com.upedge.oms.modules.pack.request.OrderPackRevokeRequest;
import com.upedge.oms.modules.pack.vo.OrderPackageInfoVo;
import com.upedge.thirdparty.shipcompany.fpx.request.OrderPackageGetLabelRequest;

import java.util.List;

public interface OrderPackageService {


    BaseResponse orderRevokePackage(OrderPackRevokeRequest request,Session session);

    List<OrderLabelPrintLog> packLabelPrintLog(Long packNo);

    BaseResponse printPackLabel(OrderPackageGetLabelRequest request, Session session);

    OrderPackageInfoVo packageInfo(Long packageNo);

    BaseResponse createPackage(Long orderId);

    OrderPackage selectByPrimaryKey(Long id,Long orderId,String trackingCode);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OrderPackage record);

    int updateByPrimaryKeySelective(OrderPackage record);

    int insert(OrderPackage record);

    int insertSelective(OrderPackage record);

    List<OrderPackage> select(Page<OrderPackage> record);

    long count(Page<OrderPackage> record);
}
