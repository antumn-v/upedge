package com.upedge.cms.modules.website.service;

import com.upedge.cms.modules.website.entity.WebsiteCommentFollow;
import com.upedge.common.base.Page;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteCommentFollowService{

    WebsiteCommentFollow selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WebsiteCommentFollow record);

    int updateByPrimaryKeySelective(WebsiteCommentFollow record);

    int insert(WebsiteCommentFollow record);

    int insertSelective(WebsiteCommentFollow record);

    List<WebsiteCommentFollow> select(Page<WebsiteCommentFollow> record);

    long count(Page<WebsiteCommentFollow> record);

    WebsiteCommentFollow queryWebsiteCommentFollow(Long commentId, Long userId);
}

