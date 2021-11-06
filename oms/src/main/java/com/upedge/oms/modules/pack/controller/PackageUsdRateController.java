package com.upedge.oms.modules.pack.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.oms.modules.pack.request.PackageUsdRateUpdateRequest;
import com.upedge.oms.modules.pack.service.PackageUsdRateService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;
/**
 * 
 *
 * @author author
 */
@RestController
@RequestMapping("/packageUsdRate")
public class PackageUsdRateController {
    @Autowired
    private PackageUsdRateService packageUsdRateService;

    /**
     * 更新月美元汇款
     * @param request
     * @return
     */
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public BaseResponse updatePackageUsdRate(@RequestBody @Valid PackageUsdRateUpdateRequest request) {
        return packageUsdRateService.updatePackageUsdRate(request);
    }



}
