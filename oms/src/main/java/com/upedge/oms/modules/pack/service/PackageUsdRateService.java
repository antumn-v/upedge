package com.upedge.oms.modules.pack.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.entity.PackageUsdRate;
import com.upedge.oms.modules.pack.request.PackageUsdRateUpdateRequest;

import java.math.BigDecimal;

/**
 * @author author
 */
public interface PackageUsdRateService{

    BigDecimal currentMonthUsdRate();

    BaseResponse updatePackageUsdRate(PackageUsdRateUpdateRequest request);

    /**
     * 获取美元汇率
     * @param format
     * @return
     */
    PackageUsdRate queryPackageUsdRate(String format);
}

