package com.upedge.oms.modules.pack.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.utils.DateUtils;
import com.upedge.oms.modules.pack.dao.PackageUsdRateDao;
import com.upedge.oms.modules.pack.entity.PackageUsdRate;
import com.upedge.oms.modules.pack.request.PackageUsdRateUpdateRequest;
import com.upedge.oms.modules.pack.service.PackageUsdRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class PackageUsdRateServiceImpl implements PackageUsdRateService {

    @Autowired
    private PackageUsdRateDao packageUsdRateDao;

    @Override
    public BigDecimal currentMonthUsdRate() {
        String monthDate = DateUtils.formatDate(new Date(),"yyyy-MM");
        BigDecimal usdRate = packageUsdRateDao.selectUsdRateByMonth(monthDate);
        if(null == usdRate){
            usdRate = new BigDecimal("6.3");
        }
        return usdRate;
    }

    /**
     * 更新月美元汇率
     * @param request
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public BaseResponse updatePackageUsdRate(PackageUsdRateUpdateRequest request) {
        if(request.getUsdRate().compareTo(BigDecimal.ONE)<=0){
          return new BaseResponse(ResultCode.FAIL_CODE,"参数异常!");
        }
        PackageUsdRate packageUsdRate=new PackageUsdRate();
        packageUsdRate.setUsdRate(request.getUsdRate());
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
        try {
            format.parse(request.getMonthDate());
        } catch (ParseException e) {
            return new BaseResponse(ResultCode.FAIL_CODE,"参数异常!");
        }
        String monthDate=request.getMonthDate();
        packageUsdRate.setMonthDate(monthDate);
        packageUsdRateDao.updatePackageUsdRate(packageUsdRate);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    @Override
    public PackageUsdRate queryPackageUsdRate(String format) {
    return     packageUsdRateDao.queryPackageUsdRate(format);
    }
}