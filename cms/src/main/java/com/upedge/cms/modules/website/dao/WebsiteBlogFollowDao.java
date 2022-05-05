package com.upedge.cms.modules.website.dao;

import com.upedge.cms.modules.website.entity.WebsiteBlogFollow;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteBlogFollowDao{

    WebsiteBlogFollow selectByPrimaryKey(WebsiteBlogFollow record);

    int deleteByPrimaryKey(WebsiteBlogFollow record);

    int updateByPrimaryKey(WebsiteBlogFollow record);

    int updateByPrimaryKeySelective(WebsiteBlogFollow record);

    int insert(WebsiteBlogFollow record);

    int insertSelective(WebsiteBlogFollow record);

    int insertByBatch(List<WebsiteBlogFollow> list);

    List<WebsiteBlogFollow> select(Page<WebsiteBlogFollow> record);

    long count(Page<WebsiteBlogFollow> record);

    WebsiteBlogFollow queryWebsiteBlogFollow(@Param("blogId")Long blogId,
         @Param("userId")Long userId);
}
