package com.upedge.oms.modules.pack.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.statistics.request.ManagerPackageStatisticsRequest;
import com.upedge.common.model.statistics.vo.ManagerPackageOrderCountVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.oms.modules.pack.entity.PackageOrderInfo;
import com.upedge.oms.modules.pack.request.OrderHandleTimeRequest;
import com.upedge.oms.modules.pack.request.PackageOrderInfoListRequest;
import com.upedge.oms.modules.pack.response.PackageOrderInfoListResponse;

import java.util.List;

/**
 * @author author
 */
public interface PackageOrderInfoService{

    BaseResponse orderHandleTimeCount(OrderHandleTimeRequest request, Session session);

    List<ManagerPackageOrderCountVo> selectManagerPackageOrderCountByDate(ManagerPackageStatisticsRequest request);

    PackageOrderInfoListResponse adminList(PackageOrderInfoListRequest request);

    PackageOrderInfo selectPackageOrderInfoByClientOrderCode(Long orderId);
}

