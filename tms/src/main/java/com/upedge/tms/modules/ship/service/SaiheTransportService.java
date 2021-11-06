package com.upedge.tms.modules.ship.service;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.SaiheTransport;
import com.upedge.tms.modules.ship.response.SaiheTransportListResponse;
import com.upedge.tms.modules.ship.response.SaiheTransportUpdateResponse;

import java.util.List;

/**
 * @author author
 */
public interface SaiheTransportService{

    SaiheTransport selectByPrimaryKey(Integer id);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(SaiheTransport record);

    int updateByPrimaryKeySelective(SaiheTransport record);

    int insert(SaiheTransport record);

    int insertSelective(SaiheTransport record);

    List<SaiheTransport> select(Page<SaiheTransport> record);

    long count(Page<SaiheTransport> record);

    SaiheTransportListResponse allSaiheTransport();

    SaiheTransportUpdateResponse refreshSaihe();
}

