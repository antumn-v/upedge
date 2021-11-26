package com.upedge.tms.modules.ship.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.tms.modules.ship.entity.ShippingUnit;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

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

    ShipDetail selectByCondition(Long methodId,
                                 Long toAreaId,
                                 BigDecimal weight);


    List<ShipDetail> selectByMethodIdsAndWeight(Set<Long> methodIds,
                                                Long toAreaId,
                                                BigDecimal weight,
                                                Integer weightType);

    ShippingUnit getShippingUnitByOption(Long methodId, String fromAreaId, String toAreaId, BigDecimal startWeight, BigDecimal endWeight);

    Integer insertBatch(List<ShippingUnit> newList);

    BaseResponse removeAllUseless();

    BaseResponse useImportAll();

    List<ShippingUnit> multiExportShippingUnits();

    void senMq(Long id);
}

