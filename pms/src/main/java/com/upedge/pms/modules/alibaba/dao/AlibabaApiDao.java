package com.upedge.pms.modules.alibaba.dao;

import com.upedge.common.base.Page;
import com.upedge.pms.modules.alibaba.entity.AlibabaApi;

import java.util.List;

/**
 * @author gx
 */
public interface AlibabaApiDao{

    AlibabaApi selectUnExpireApi(long timestamp);

    AlibabaApi selectAlibabaApi();

    AlibabaApi selectByPrimaryKey(AlibabaApi record);

    int deleteByPrimaryKey(AlibabaApi record);

    int updateByPrimaryKey(AlibabaApi record);

    int updateByPrimaryKeySelective(AlibabaApi record);

    List<AlibabaApi> select(Page<AlibabaApi> record);

    long count(Page<AlibabaApi> record);

}
