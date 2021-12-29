package com.upedge.tms.modules.ship.vo;

import com.upedge.common.model.tms.ShippingTemplateVo;
import lombok.Data;

import java.util.List;
@Data
public class MethodIdTemplateNameVo {

    Long methodId;

    List<ShippingTemplateVo> shippingTemplateVos;
}
