package com.upedge.cms.modules.website.service.impl;

import com.upedge.cms.modules.website.dao.WebsiteBlogFollowDao;
import com.upedge.cms.modules.website.entity.WebsiteBlogFollow;
import com.upedge.cms.modules.website.service.WebsiteBlogFollowService;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WebsiteBlogFollowServiceImpl implements WebsiteBlogFollowService {

    @Autowired
    private WebsiteBlogFollowDao websiteBlogFollowDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WebsiteBlogFollow record = new WebsiteBlogFollow();
        record.setId(id);
        return websiteBlogFollowDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WebsiteBlogFollow record) {
        return websiteBlogFollowDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WebsiteBlogFollow record) {
        return websiteBlogFollowDao.insert(record);
    }

    /**
     *
     */
    public WebsiteBlogFollow selectByPrimaryKey(Long id){
        WebsiteBlogFollow record = new WebsiteBlogFollow();
        record.setId(id);
        return websiteBlogFollowDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(WebsiteBlogFollow record) {
        return websiteBlogFollowDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WebsiteBlogFollow record) {
        return websiteBlogFollowDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WebsiteBlogFollow> select(Page<WebsiteBlogFollow> record){
        record.initFromNum();
        return websiteBlogFollowDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WebsiteBlogFollow> record){
        return websiteBlogFollowDao.count(record);
    }

    @Override
    public WebsiteBlogFollow queryWebsiteBlogFollow(Long blogId, String appUserId) {
        return websiteBlogFollowDao.queryWebsiteBlogFollow(blogId,appUserId);
    }
}