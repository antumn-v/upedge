package com.upedge.pms.modules.alibaba.service;

import com.upedge.pms.modules.alibaba.entity.AlibabaApi;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface AlibabaApiService{

    AlibabaApi selectAlibabaApi();

    AlibabaApi selectUnExpireApi(long timestamp);

    AlibabaApi selectByPrimaryKey(String apiKey);

    int deleteByPrimaryKey(String apiKey);

    int updateByPrimaryKey(AlibabaApi record);

    int updateByPrimaryKeySelective(AlibabaApi record);

    List<AlibabaApi> select(Page<AlibabaApi> record);

    long count(Page<AlibabaApi> record);
}

