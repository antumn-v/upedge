package com.upedge.oms.modules.pack.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.entity.PackageUsdRate;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface PackageUsdRateDao{

    BigDecimal selectUsdRateByMonth(String monthDate);

    PackageUsdRate selectByPrimaryKey(String monthDate);

    int deleteByPrimaryKey(PackageUsdRate record);

    int updateByPrimaryKey(PackageUsdRate record);

    int updateByPrimaryKeySelective(PackageUsdRate record);

    int insert(PackageUsdRate record);

    int insertSelective(PackageUsdRate record);

    int insertByBatch(List<PackageUsdRate> list);

    List<PackageUsdRate> select(Page<PackageUsdRate> record);

    long count(Page<PackageUsdRate> record);

    PackageUsdRate queryPackageUsdRate(String date);

    //更新月包裹美元汇率
    void updatePackageUsdRate(PackageUsdRate packageUsdRate);

}
