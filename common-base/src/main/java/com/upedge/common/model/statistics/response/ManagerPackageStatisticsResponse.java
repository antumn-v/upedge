package com.upedge.common.model.statistics.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.statistics.vo.ManagerPackageStatisticsVo;

import java.util.List;

public class ManagerPackageStatisticsResponse extends BaseResponse {

    public ManagerPackageStatisticsResponse(int code, String msg, List<ManagerPackageStatisticsVo> managerPackageStatisticsVos) {
        super(code, msg, managerPackageStatisticsVos);
    }

    public ManagerPackageStatisticsResponse(int code, String msg, List<ManagerPackageStatisticsVo> managerPackageStatisticsVos, Object req) {
        super(code, msg, managerPackageStatisticsVos, req);
    }

    public ManagerPackageStatisticsResponse() {
    }
}
