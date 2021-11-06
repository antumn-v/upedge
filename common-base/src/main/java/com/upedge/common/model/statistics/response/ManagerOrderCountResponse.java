package com.upedge.common.model.statistics.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.statistics.vo.ManagerPackageOrderCountVo;

import java.util.List;

public class ManagerOrderCountResponse  extends BaseResponse {
    public ManagerOrderCountResponse(int code, String msg, List<ManagerPackageOrderCountVo> managerPackageOrderCountVos, Object req) {
        super(code, msg, managerPackageOrderCountVos, req);
    }

    public ManagerOrderCountResponse() {
    }
}
