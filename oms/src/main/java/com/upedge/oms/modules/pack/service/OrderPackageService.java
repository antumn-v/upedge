package com.upedge.oms.modules.pack.service;

import com.upedge.common.base.BaseResponse;

public interface OrderPackageService {


    BaseResponse createFpxPackage(Long orderId);
}
