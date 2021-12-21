package com.upedge.cms.modules.website.dao;

import com.upedge.cms.modules.website.entity.WebsiteRemark;
import com.upedge.common.base.Page;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteRemarkDao{

    WebsiteRemark selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WebsiteRemark record);

    int updateByPrimaryKeySelective(WebsiteRemark record);

    int insert(WebsiteRemark record);

    int insertSelective(WebsiteRemark record);

    int insertByBatch(List<WebsiteRemark> list);

    List<WebsiteRemark> select(Page<WebsiteRemark> record);

    long count(Page<WebsiteRemark> record);

    void updateState(WebsiteRemark websiteRemark);

    List<WebsiteRemark> listWebsiteRemark();
}
