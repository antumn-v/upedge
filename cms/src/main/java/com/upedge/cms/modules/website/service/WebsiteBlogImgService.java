package com.upedge.cms.modules.website.service;

import com.upedge.cms.modules.website.entity.WebsiteBlogImg;
import com.upedge.common.base.Page;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteBlogImgService{

    WebsiteBlogImg selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WebsiteBlogImg record);

    int updateByPrimaryKeySelective(WebsiteBlogImg record);

    int insert(WebsiteBlogImg record);

    int insertSelective(WebsiteBlogImg record);

    List<WebsiteBlogImg> select(Page<WebsiteBlogImg> record);

    long count(Page<WebsiteBlogImg> record);
}

