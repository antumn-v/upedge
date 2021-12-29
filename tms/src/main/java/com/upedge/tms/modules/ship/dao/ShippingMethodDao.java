package com.upedge.tms.modules.ship.dao;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.ShippingMethod;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface ShippingMethodDao{


    ShippingMethod selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(ShippingMethod record);

    int updateByPrimaryKey(ShippingMethod record);

    int updateByPrimaryKeySelective(ShippingMethod record);

    int insert(ShippingMethod record);

    int insertSelective(ShippingMethod record);

    int insertByBatch(List<ShippingMethod> list);

    List<ShippingMethod> select(Page<ShippingMethod> record);

    long count(Page<ShippingMethod> record);

    void updateShippingMethodState(@Param("id") Long id, @Param("state") int state);

    List<String> listUseAllShippingMethodName();

    ShippingMethod getShippingMethodByName(String methodName);

    List<ShippingMethod> allShippingMethod();

    List<ShippingMethod> listShippingMethod(@Param("ids") List<Long> ids);

    ShippingMethod getShippingMethodByTransportId(Integer transportId);

    /**
     * 修改赛盒运输信息时维护冗余字段
     * @param id
     * @param transportName
     * @return
     */
    int updateBySaiheTransport(@Param("saiheTransportId") Integer id, @Param("transportName") String transportName);
}
