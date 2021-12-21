package com.upedge.cms.modules.website.service;

import com.upedge.cms.modules.website.entity.WebsiteBlogFollow;
import com.upedge.common.base.Page;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteBlogFollowService{

    WebsiteBlogFollow selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WebsiteBlogFollow record);

    int updateByPrimaryKeySelective(WebsiteBlogFollow record);

    int insert(WebsiteBlogFollow record);

    int insertSelective(WebsiteBlogFollow record);

    List<WebsiteBlogFollow> select(Page<WebsiteBlogFollow> record);

    long count(Page<WebsiteBlogFollow> record);

    WebsiteBlogFollow queryWebsiteBlogFollow(Long blogId, String appUserId);
}

