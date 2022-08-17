package com.upedge.oms.modules.pack.vo;

import com.upedge.oms.modules.order.vo.AppOrderVo;
import com.upedge.oms.modules.pack.entity.OrderPackage;
import lombok.Data;

@Data
public class OrderPackageInfoVo extends OrderPackage {

    private Integer pickType;

    private AppOrderVo orderVo;
}
