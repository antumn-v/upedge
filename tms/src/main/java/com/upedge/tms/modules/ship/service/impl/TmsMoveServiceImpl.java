package com.upedge.tms.modules.ship.service.impl;

import com.upedge.common.model.old.tms.ShippingMethod;
import com.upedge.common.model.old.tms.ShippingTemplate;
import com.upedge.common.model.old.tms.ShippingTemplateMethod;
import com.upedge.tms.modules.ship.dao.ShippingMethodDao;
import com.upedge.tms.modules.ship.dao.ShippingMethodTemplateDao;
import com.upedge.tms.modules.ship.dao.ShippingTemplateDao;
import com.upedge.tms.modules.ship.entity.ShippingMethodTemplate;
import com.upedge.tms.modules.ship.service.TmsMoveService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TmsMoveServiceImpl implements TmsMoveService {

    @Autowired
    ShippingMethodDao shippingMethodDao;

    @Autowired
    ShippingTemplateDao shippingTemplateDao;


    @Autowired
    ShippingMethodTemplateDao shippingMethodTemplateDao;

    @Transactional
    @Override
    public void saveShipMethods(List<ShippingMethod> shippingMethods,
                                List<ShippingTemplate> shippingTemplates,
                                List<ShippingTemplateMethod> shippingTemplateMethods) {
        List<com.upedge.tms.modules.ship.entity.ShippingMethod> shippingMethodList = new ArrayList<>();

        shippingMethods.forEach(shippingMethod -> {
            com.upedge.tms.modules.ship.entity.ShippingMethod method = new com.upedge.tms.modules.ship.entity.ShippingMethod();
            BeanUtils.copyProperties(shippingMethod,method);
            shippingMethodList.add(method);
        });
        shippingMethodDao.insertByBatch(shippingMethodList);

        List<com.upedge.tms.modules.ship.entity.ShippingTemplate> shippingTemplateList = new ArrayList<>();

        shippingTemplates.forEach(shippingTemplate -> {
            com.upedge.tms.modules.ship.entity.ShippingTemplate template = new com.upedge.tms.modules.ship.entity.ShippingTemplate();
            BeanUtils.copyProperties(shippingTemplate,template);
            shippingTemplate.setStatus(template.getState());
            shippingTemplateList.add(template);
        });
        shippingTemplateDao.insertByBatch(shippingTemplateList);


        List<ShippingMethodTemplate> shippingMethodTemplates = new ArrayList<>();

        for (ShippingTemplateMethod shippingTemplateMethod : shippingTemplateMethods) {
            ShippingMethodTemplate shippingMethodTemplate = new ShippingMethodTemplate();
            BeanUtils.copyProperties(shippingTemplateMethod,shippingMethodTemplate);
            shippingMethodTemplates.add(shippingMethodTemplate);
        }

        shippingMethodTemplateDao.insertByBatch(shippingMethodTemplates);

    }


}
