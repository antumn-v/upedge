package com.upedge.tms.modules.ship.dao;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.ShippingMethod;
import com.upedge.tms.modules.ship.entity.ShippingMethodTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface ShippingMethodTemplateDao{

    List<ShippingMethod> selectMethodIdsByTemplate(Long templateId);

    ShippingMethodTemplate selectByPrimaryKey(ShippingMethodTemplate record);

    int deleteByPrimaryKey(ShippingMethodTemplate record);

    int updateByPrimaryKey(ShippingMethodTemplate record);

    int updateByPrimaryKeySelective(ShippingMethodTemplate record);

    int insert(ShippingMethodTemplate record);

    int insertSelective(ShippingMethodTemplate record);

    int insertByBatch(List<ShippingMethodTemplate> list);

    List<ShippingMethodTemplate> select(Page<ShippingMethodTemplate> record);

    long count(Page<ShippingMethodTemplate> record);

    void updateSort(List<ShippingMethodTemplate> list);

    ShippingMethodTemplate queryMethodTemplate(@Param("shippingId") Long shippingId, @Param("methodId") Long methodId);

    int countShipMethod(@Param("shippingId") Long shippingId);

    int countShipNum(@Param("methodId") Long methodId);

    List<ShippingMethodTemplate> listShippingMethodTemplate();
}
