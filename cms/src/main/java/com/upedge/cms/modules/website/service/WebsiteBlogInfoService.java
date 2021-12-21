package com.upedge.cms.modules.website.service;

import com.upedge.cms.modules.website.entity.WebsiteBlogInfo;
import com.upedge.cms.modules.website.request.WebsiteBlogInfoAddRequest;
import com.upedge.cms.modules.website.request.WebsiteBlogInfoListRequest;
import com.upedge.cms.modules.website.request.WebsiteBlogInfoUpdateRequest;
import com.upedge.cms.modules.website.response.WebsiteBlogInfoAddResponse;
import com.upedge.cms.modules.website.response.WebsiteBlogInfoInfoResponse;
import com.upedge.cms.modules.website.response.WebsiteBlogInfoListResponse;
import com.upedge.cms.modules.website.response.WebsiteBlogInfoUpdateResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteBlogInfoService{

    WebsiteBlogInfo selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WebsiteBlogInfo record);

    int updateByPrimaryKeySelective(WebsiteBlogInfo record);

    int insert(WebsiteBlogInfo record);

    int insertSelective(WebsiteBlogInfo record);

    List<WebsiteBlogInfo> select(Page<WebsiteBlogInfo> record);

    long count(Page<WebsiteBlogInfo> record);

    WebsiteBlogInfoListResponse adminList(WebsiteBlogInfoListRequest request);

    WebsiteBlogInfoInfoResponse adminInfo(Long id);

    WebsiteBlogInfoAddResponse addBlogInfo(WebsiteBlogInfoAddRequest request, Session session);

    WebsiteBlogInfoUpdateResponse updateBlogInfo(WebsiteBlogInfoUpdateRequest request, Session session);

    WebsiteBlogInfoUpdateResponse enableBlogInfo(Long id, Session session);

    WebsiteBlogInfoUpdateResponse disableBlogInfo(Long id, Session session);

    List<String> listRouters();

    List<WebsiteBlogInfo> listBlog();

    void followBlog(Long blogId);

    List<WebsiteBlogInfo> blogSearch(String info);

    WebsiteBlogInfo queryBlogByUrlSuf(String urlSuf);

    WebsiteBlogInfo viewBlog(Long id);

    void cancelBlog(Long blogId);
}

