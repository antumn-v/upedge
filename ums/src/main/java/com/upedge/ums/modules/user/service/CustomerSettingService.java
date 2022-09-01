package com.upedge.ums.modules.user.service;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.CustomerSetting;

import java.util.List;

/**
 * @author gx
 */
public interface CustomerSettingService{

    void saveNewSetting();

    CustomerSetting selectByCustomerAndSettingName(Long customerId,
                                                   String settingName);

    CustomerSetting selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(CustomerSetting record);

    int updateByPrimaryKeySelective(CustomerSetting record);

    int insert(CustomerSetting record);

    int insertByBatch(List<CustomerSetting> customerSettings);

    int insertSelective(CustomerSetting record);

    List<CustomerSetting> select(Page<CustomerSetting> record);

    long count(Page<CustomerSetting> record);
}

