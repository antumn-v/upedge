package com.upedge.tms.modules.ship.dao;

import com.upedge.common.base.Page;
import com.upedge.tms.modules.ship.entity.SaiheTransport;

import java.util.List;

/**
 * @author author
 */
public interface SaiheTransportDao{

    SaiheTransport selectByPrimaryKey(SaiheTransport record);

    int deleteByPrimaryKey(SaiheTransport record);

    int updateByPrimaryKey(SaiheTransport record);

    int updateByPrimaryKeySelective(SaiheTransport record);

    int insert(SaiheTransport record);

    int insertSelective(SaiheTransport record);

    int saveSaiheTransport(List<SaiheTransport> list);

    List<SaiheTransport> select(Page<SaiheTransport> record);

    long count(Page<SaiheTransport> record);

    List<SaiheTransport> allSaiheTransport();
}
