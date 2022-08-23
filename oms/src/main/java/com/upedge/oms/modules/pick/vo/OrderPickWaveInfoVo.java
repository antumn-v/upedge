package com.upedge.oms.modules.pick.vo;

import com.upedge.oms.modules.order.vo.AppOrderVo;
import com.upedge.oms.modules.pick.entity.OrderPick;
import lombok.Data;

import java.util.List;
@Data
public class OrderPickWaveInfoVo extends OrderPick {

    private List<AppOrderVo> orderVos;
}
