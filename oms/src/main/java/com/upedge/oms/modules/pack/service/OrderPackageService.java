package com.upedge.oms.modules.pack.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.oms.modules.pack.entity.OrderPackage;
import com.upedge.oms.modules.pack.vo.OrderPackageInfoVo;

import java.util.List;

public interface OrderPackageService {


    OrderPackageInfoVo packageInfo(Long packageNo);

    BaseResponse createPackage(Long orderId);

    OrderPackage selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(OrderPackage record);

    int updateByPrimaryKeySelective(OrderPackage record);

    int insert(OrderPackage record);

    int insertSelective(OrderPackage record);

    List<OrderPackage> select(Page<OrderPackage> record);

    long count(Page<OrderPackage> record);
}
