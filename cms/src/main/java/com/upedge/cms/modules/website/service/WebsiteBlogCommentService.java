package com.upedge.cms.modules.website.service;

import com.upedge.cms.modules.website.entity.WebsiteBlogComment;
import com.upedge.cms.modules.website.request.WebsiteBlogCommentListRequest;
import com.upedge.cms.modules.website.response.WebsiteBlogCommentListResponse;
import com.upedge.cms.modules.website.response.WebsiteBlogCommentUpdateResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteBlogCommentService{

    WebsiteBlogComment selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WebsiteBlogComment record);

    int updateByPrimaryKeySelective(WebsiteBlogComment record);

    int insert(WebsiteBlogComment record);

    int insertSelective(WebsiteBlogComment record);

    List<WebsiteBlogComment> select(Page<WebsiteBlogComment> record);

    long count(Page<WebsiteBlogComment> record);

    WebsiteBlogCommentListResponse adminList(WebsiteBlogCommentListRequest request);

    WebsiteBlogCommentUpdateResponse disableComment(Long id, Session session);

    WebsiteBlogCommentUpdateResponse enableComment(Long id, Session session);

    void followComment(Long commentId);

    Long countWebsiteRemarkByBlogId(Long id);

    List<WebsiteBlogComment> listComment(Long id);
}

