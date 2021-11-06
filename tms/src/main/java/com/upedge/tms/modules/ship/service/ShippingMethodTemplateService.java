package com.upedge.tms.modules.ship.service;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.ShippingMethodTemplate;
import com.upedge.tms.modules.ship.request.ShippingMethodTemplateAddRequest;
import com.upedge.tms.modules.ship.request.ShippingMethodTemplateListRequest;
import com.upedge.tms.modules.ship.request.ShippingMethodTemplateUpdateRequest;
import com.upedge.tms.modules.ship.response.ShippingMethodTemplateAddResponse;
import com.upedge.tms.modules.ship.response.ShippingMethodTemplateListResponse;
import com.upedge.tms.modules.ship.response.ShippingMethodTemplateUpdateResponse;

import java.util.List;

/**
 * @author author
 */
public interface ShippingMethodTemplateService{

    ShippingMethodTemplate selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ShippingMethodTemplate record);

    int updateByPrimaryKeySelective(ShippingMethodTemplate record);

    int insert(ShippingMethodTemplate record);

    int insertSelective(ShippingMethodTemplate record);

    List<ShippingMethodTemplate> select(Page<ShippingMethodTemplate> record);

    long count(Page<ShippingMethodTemplate> record);

    ShippingMethodTemplateUpdateResponse updateSort(ShippingMethodTemplateUpdateRequest request);

    ShippingMethodTemplateListResponse listMethodTemplate(ShippingMethodTemplateListRequest request);

    ShippingMethodTemplateAddResponse addShippingMethodTemplate(ShippingMethodTemplateAddRequest request);

    List<ShippingMethodTemplate> listShippingMethodTemplate();

}

