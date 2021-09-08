package com.upedge.ums.modules.user.dao;

import com.upedge.ums.modules.user.entity.CustomerSetting;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

/**
 * @author gx
 */
public interface CustomerSettingDao{

    CustomerSetting selectByCustomerAndSettingName(@Param("customerId")Long customerId,
                                                   @Param("settingName")String settingName);

    CustomerSetting selectByPrimaryKey(CustomerSetting record);

    int deleteByPrimaryKey(CustomerSetting record);

    int updateByPrimaryKey(CustomerSetting record);

    int updateByPrimaryKeySelective(CustomerSetting record);

    int insert(CustomerSetting record);

    int insertSelective(CustomerSetting record);

    int insertByBatch(List<CustomerSetting> list);

    List<CustomerSetting> select(Page<CustomerSetting> record);

    long count(Page<CustomerSetting> record);

}
