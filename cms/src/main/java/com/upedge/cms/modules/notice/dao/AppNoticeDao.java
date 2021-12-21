package com.upedge.cms.modules.notice.dao;

import com.upedge.cms.modules.notice.entity.AppNotice;
import com.upedge.common.base.Page;

import java.util.List;

/**
 * @author author
 */
public interface AppNoticeDao{

    AppNotice selectRecentlyNotice();

    int addViewCountById(Long id);

    AppNotice selectByPrimaryKey(Long id);

    int updateByPrimaryKey(AppNotice record);

    int updateByPrimaryKeySelective(AppNotice record);

    int insert(AppNotice record);

    List<AppNotice> select(Page<AppNotice> record);

    long count(Page<AppNotice> record);

    void updateState(AppNotice appNotice);
}
