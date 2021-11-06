package com.upedge.tms.modules.ship.service;

import com.upedge.common.model.old.tms.ShippingMethod;
import com.upedge.common.model.old.tms.ShippingTemplate;
import com.upedge.common.model.old.tms.ShippingTemplateMethod;

import java.util.List;

public interface TmsMoveService {

    void saveShipMethods(List<ShippingMethod> shippingMethods, List<ShippingTemplate> shippingTemplates, List<ShippingTemplateMethod> shippingTemplateMethods);



}
