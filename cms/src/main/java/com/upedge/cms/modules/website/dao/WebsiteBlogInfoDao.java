package com.upedge.cms.modules.website.dao;

import com.upedge.cms.modules.website.entity.WebsiteBlogInfo;
import com.upedge.common.base.Page;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteBlogInfoDao{

    WebsiteBlogInfo selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(WebsiteBlogInfo record);

    int updateByPrimaryKey(WebsiteBlogInfo record);

    int updateByPrimaryKeySelective(WebsiteBlogInfo record);

    int insert(WebsiteBlogInfo record);

    int insertSelective(WebsiteBlogInfo record);

    int insertByBatch(List<WebsiteBlogInfo> list);

    List<WebsiteBlogInfo> select(Page<WebsiteBlogInfo> record);

    long count(Page<WebsiteBlogInfo> record);

    void updateState(WebsiteBlogInfo websiteBlogInfo);

    List<String> listRouters();

    List<WebsiteBlogInfo> listBlog();

    void followBlog(Long id);

    void cancelBlog(Long id);

    List<WebsiteBlogInfo> blogSearch(String info);

    WebsiteBlogInfo queryBlogByUrlSuf(String urlSuf);

    void viewBlog(Long id);

    WebsiteBlogInfo queryBlog(Long id);
}
