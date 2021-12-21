package com.upedge.cms.modules.website.dao;

import com.upedge.cms.modules.website.entity.WebsiteCommentFollow;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteCommentFollowDao{

    WebsiteCommentFollow selectByPrimaryKey(WebsiteCommentFollow record);

    int deleteByPrimaryKey(WebsiteCommentFollow record);

    int updateByPrimaryKey(WebsiteCommentFollow record);

    int updateByPrimaryKeySelective(WebsiteCommentFollow record);

    int insert(WebsiteCommentFollow record);

    int insertSelective(WebsiteCommentFollow record);

    int insertByBatch(List<WebsiteCommentFollow> list);

    List<WebsiteCommentFollow> select(Page<WebsiteCommentFollow> record);

    long count(Page<WebsiteCommentFollow> record);

    WebsiteCommentFollow queryWebsiteCommentFollow(@Param("commentId") Long commentId,
            @Param("appUserId")String appUserId);
}
