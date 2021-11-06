package com.upedge.tms.modules.ship.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.ship.vo.ShipDetail;
import com.upedge.common.model.ship.vo.ShipMethodNameVo;
import com.upedge.tms.modules.ship.entity.ShippingUnit;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author author
 */
public interface ShippingUnitDao{

    List<ShipDetail> selectByMethodIdsAndWeight(@Param("methodIds") Set<Long> methodIds,
                                                @Param("toAreaId") Long toAreaId,
                                                @Param("weight") BigDecimal weight,
                                                @Param("volumn") BigDecimal volunmn);

    List<ShipMethodNameVo> selectShipNameByToAreaId(Long areaId);

    ShipDetail selectByCondition(@Param("methodId") Long methodId,
                                 @Param("toAreaId") Long toAreaId,
                                 @Param("weight") BigDecimal weight);

    ShippingUnit selectByPrimaryKey(ShippingUnit record);

    int deleteByPrimaryKey(ShippingUnit record);

    int updateByPrimaryKey(ShippingUnit record);

    int updateByPrimaryKeySelective(ShippingUnit record);

    int insert(ShippingUnit record);

    int insertSelective(ShippingUnit record);

    int insertByBatch(List<ShippingUnit> list);

    List<ShippingUnit> select(Page<ShippingUnit> record);

    long count(Page<ShippingUnit> record);

    ShippingUnit getShippingUnitByOption(@Param("methodId") Long methodId,
                                         @Param("fromAreaId") String fromAreaId,
                                         @Param("toAreaId") String toAreaId, @Param("startWeight") BigDecimal startWeight,
                                         @Param("endWeight") BigDecimal endWeight);

    int removeAllUseless();

    int useImportAll();

    List<ShippingUnit> multiExportShippingUnits();

    int countUnit(@Param("methodId") Long methodId);

    List<ShippingUnit> selectListByShippingMethodId(@Param("shippingMethodId") Long shippingMethodId);
}
