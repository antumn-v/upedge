package com.upedge.cms.modules.website.service.impl;

import com.upedge.cms.modules.website.dao.WebsiteCommentFollowDao;
import com.upedge.cms.modules.website.entity.WebsiteCommentFollow;
import com.upedge.cms.modules.website.service.WebsiteCommentFollowService;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WebsiteCommentFollowServiceImpl implements WebsiteCommentFollowService {

    @Autowired
    private WebsiteCommentFollowDao websiteCommentFollowDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WebsiteCommentFollow record = new WebsiteCommentFollow();
        record.setId(id);
        return websiteCommentFollowDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WebsiteCommentFollow record) {
        return websiteCommentFollowDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WebsiteCommentFollow record) {
        return websiteCommentFollowDao.insert(record);
    }

    /**
     *
     */
    public WebsiteCommentFollow selectByPrimaryKey(Long id){
        WebsiteCommentFollow record = new WebsiteCommentFollow();
        record.setId(id);
        return websiteCommentFollowDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(WebsiteCommentFollow record) {
        return websiteCommentFollowDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WebsiteCommentFollow record) {
        return websiteCommentFollowDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WebsiteCommentFollow> select(Page<WebsiteCommentFollow> record){
        record.initFromNum();
        return websiteCommentFollowDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WebsiteCommentFollow> record){
        return websiteCommentFollowDao.count(record);
    }

    @Override
    public WebsiteCommentFollow queryWebsiteCommentFollow(Long commentId, String appUserId) {
        return websiteCommentFollowDao.queryWebsiteCommentFollow(commentId,appUserId);
    }
}