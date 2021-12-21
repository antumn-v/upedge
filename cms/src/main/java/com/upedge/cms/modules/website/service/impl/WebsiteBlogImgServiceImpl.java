package com.upedge.cms.modules.website.service.impl;

import com.upedge.cms.modules.website.dao.WebsiteBlogImgDao;
import com.upedge.cms.modules.website.entity.WebsiteBlogImg;
import com.upedge.cms.modules.website.service.WebsiteBlogImgService;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WebsiteBlogImgServiceImpl implements WebsiteBlogImgService {

    @Autowired
    private WebsiteBlogImgDao websiteBlogImgDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WebsiteBlogImg record = new WebsiteBlogImg();
        record.setId(id);
        return websiteBlogImgDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WebsiteBlogImg record) {
        return websiteBlogImgDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WebsiteBlogImg record) {
        return websiteBlogImgDao.insert(record);
    }

    /**
     *
     */
    public WebsiteBlogImg selectByPrimaryKey(Long id){
        WebsiteBlogImg record = new WebsiteBlogImg();
        record.setId(id);
        return websiteBlogImgDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(WebsiteBlogImg record) {
        return websiteBlogImgDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WebsiteBlogImg record) {
        return websiteBlogImgDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WebsiteBlogImg> select(Page<WebsiteBlogImg> record){
        record.initFromNum();
        return websiteBlogImgDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WebsiteBlogImg> record){
        return websiteBlogImgDao.count(record);
    }

}