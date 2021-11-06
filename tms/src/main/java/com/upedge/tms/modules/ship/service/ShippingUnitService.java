package com.upedge.tms.modules.ship.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.ShippingUnit;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author author
 */
public interface ShippingUnitService{

    ShippingUnit selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(ShippingUnit record);

    int updateByPrimaryKeySelective(ShippingUnit record);

    int insert(ShippingUnit record);

    int insertSelective(ShippingUnit record);

    List<ShippingUnit> select(Page<ShippingUnit> record);

    long count(Page<ShippingUnit> record);

    ShippingUnit getShippingUnitByOption(Long methodId, String fromAreaId, String toAreaId, BigDecimal startWeight, BigDecimal endWeight);

    Integer insertBatch(List<ShippingUnit> newList);

    BaseResponse removeAllUseless();

    BaseResponse useImportAll();

    List<ShippingUnit> multiExportShippingUnits();

    void senMq(Long id);
}

