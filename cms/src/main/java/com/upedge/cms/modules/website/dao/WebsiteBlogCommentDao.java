package com.upedge.cms.modules.website.dao;

import com.upedge.cms.modules.website.entity.WebsiteBlogComment;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteBlogCommentDao{

    WebsiteBlogComment selectByPrimaryKey(WebsiteBlogComment record);

    int deleteByPrimaryKey(WebsiteBlogComment record);

    int updateByPrimaryKey(WebsiteBlogComment record);

    int updateByPrimaryKeySelective(WebsiteBlogComment record);

    int insert(WebsiteBlogComment record);

    int insertSelective(WebsiteBlogComment record);

    int insertByBatch(List<WebsiteBlogComment> list);

    List<WebsiteBlogComment> select(Page<WebsiteBlogComment> record);

    long count(Page<WebsiteBlogComment> record);

    void updateState(@Param("id") Long id,@Param("state") Integer state);

    void followComment(Long id);

    Long countWebsiteRemarkByBlogId(Long blogId);

    List<WebsiteBlogComment> listComment(Long id);
}
