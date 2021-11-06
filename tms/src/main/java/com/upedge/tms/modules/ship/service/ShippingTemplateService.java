package com.upedge.tms.modules.ship.service;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.ShippingTemplate;
import com.upedge.tms.modules.ship.request.ShippingTemplateListRequest;
import com.upedge.tms.modules.ship.response.ShippingTemplateDisableResponse;
import com.upedge.tms.modules.ship.response.ShippingTemplateEnableResponse;
import com.upedge.tms.modules.ship.response.ShippingTemplateListResponse;

import java.util.List;

/**
 * @author author
 */
public interface ShippingTemplateService{



    ShippingTemplate selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ShippingTemplate record);

    int updateByPrimaryKeySelective(ShippingTemplate record);

    int insert(ShippingTemplate record);

    int insertSelective(ShippingTemplate record);

    List<ShippingTemplate> select(Page<ShippingTemplate> record);

    long count(Page<ShippingTemplate> record);

    ShippingTemplateListResponse allShippingTemplate();

    ShippingTemplateEnableResponse enableShippingTemplate(Long id);

    ShippingTemplateDisableResponse disableShippingTemplate(Long id);

    ShippingTemplateListResponse list(ShippingTemplateListRequest request);

    List<ShippingTemplate> getAll();
}

