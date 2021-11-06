package com.upedge.oms.modules.pack.response;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.vo.ManagerPackageCountVo;
import com.upedge.oms.modules.pack.vo.PackageCountVo;
import com.upedge.oms.modules.pack.vo.PackageDailyCountVo;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PackageManagerResponse extends BaseResponse {

    PackageManagerData data;

    @Data
    public static class PackageManagerData{
        List<PackageDailyCountVo> packageDailyCountVos;

        List<ManagerPackageCountVo> managerPackageCountVos;

        PackageCountVo packageCountVo;

        BigDecimal usdRate = new BigDecimal("6.3");
    }

    public PackageManagerResponse(int code, String msg) {
        super(code, msg);
    }

    public PackageManagerResponse(int code, PackageManagerData data) {
        super(code, data);
    }

    public PackageManagerResponse(int code, String msg, PackageManagerData data) {
        super(code, msg, data);
    }

    public PackageManagerResponse(int code, String msg, PackageManagerData data, Object req) {
        super(code, msg, data, req);
    }





}
