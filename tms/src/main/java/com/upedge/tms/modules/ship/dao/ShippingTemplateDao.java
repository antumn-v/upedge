package com.upedge.tms.modules.ship.dao;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.ShippingTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface ShippingTemplateDao{

    ShippingTemplate selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(ShippingTemplate record);

    int updateByPrimaryKey(ShippingTemplate record);

    int updateByPrimaryKeySelective(ShippingTemplate record);

    int insert(ShippingTemplate record);

    int insertSelective(ShippingTemplate record);

    int insertByBatch(List<ShippingTemplate> list);

    List<ShippingTemplate> select(Page<ShippingTemplate> record);

    long count(Page<ShippingTemplate> record);

    List<ShippingTemplate> allUseShippingTemplate();

    void updateShippingTemplateState(@Param("id") Long id, @Param("state") int state);
}
