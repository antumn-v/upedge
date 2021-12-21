package com.upedge.cms.modules.website.dao;

import com.upedge.cms.modules.website.entity.WebsiteBlogImg;
import com.upedge.common.base.Page;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteBlogImgDao{

    WebsiteBlogImg selectByPrimaryKey(WebsiteBlogImg record);

    int deleteByPrimaryKey(WebsiteBlogImg record);

    int updateByPrimaryKey(WebsiteBlogImg record);

    int updateByPrimaryKeySelective(WebsiteBlogImg record);

    int insert(WebsiteBlogImg record);

    int insertSelective(WebsiteBlogImg record);

    int insertByBatch(List<WebsiteBlogImg> list);

    List<WebsiteBlogImg> select(Page<WebsiteBlogImg> record);

    long count(Page<WebsiteBlogImg> record);

}
