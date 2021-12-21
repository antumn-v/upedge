package com.upedge.cms.modules.website.service.impl;

import com.upedge.cms.modules.website.dao.WebsiteBlogCommentDao;
import com.upedge.cms.modules.website.entity.WebsiteBlogComment;
import com.upedge.cms.modules.website.request.WebsiteBlogCommentListRequest;
import com.upedge.cms.modules.website.response.WebsiteBlogCommentListResponse;
import com.upedge.cms.modules.website.response.WebsiteBlogCommentUpdateResponse;
import com.upedge.cms.modules.website.service.WebsiteBlogCommentService;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.feign.UmsFeignClient;
import com.upedge.common.model.user.vo.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WebsiteBlogCommentServiceImpl implements WebsiteBlogCommentService {

    @Autowired
    private WebsiteBlogCommentDao websiteBlogCommentDao;

    @Autowired
    private UmsFeignClient umsFeignClient;

    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WebsiteBlogComment record = new WebsiteBlogComment();
        record.setId(id);
        return websiteBlogCommentDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WebsiteBlogComment record) {
        return websiteBlogCommentDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WebsiteBlogComment record) {
        return websiteBlogCommentDao.insert(record);
    }

    /**
     *
     */
    public WebsiteBlogComment selectByPrimaryKey(Long id){
        WebsiteBlogComment record = new WebsiteBlogComment();
        record.setId(id);
        return websiteBlogCommentDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(WebsiteBlogComment record) {
        return websiteBlogCommentDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WebsiteBlogComment record) {
        return websiteBlogCommentDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WebsiteBlogComment> select(Page<WebsiteBlogComment> record){
        record.initFromNum();
        return websiteBlogCommentDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WebsiteBlogComment> record){
        return websiteBlogCommentDao.count(record);
    }

    /**
     * 博客评论列表
     * @return
     */
    @Override
    public WebsiteBlogCommentListResponse adminList(WebsiteBlogCommentListRequest request) {
        if(request.getT()==null){
            return new WebsiteBlogCommentListResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL,null,null);
        }
        WebsiteBlogComment websiteBlogComment=request.getT();
        if(websiteBlogComment.getBlogId()==null){
            return new WebsiteBlogCommentListResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL,null,null);
        }
        request.setT(websiteBlogComment);
        request.initFromNum();
        List<WebsiteBlogComment> results = websiteBlogCommentDao.select(request);
        Long total = websiteBlogCommentDao.count(request);
        request.setTotal(total);
        return new WebsiteBlogCommentListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,request);
    }

    /**
     * 禁用博客评论
     * @param id
     * @param session
     * @return
     */
    @Transactional
    @Override
    public WebsiteBlogCommentUpdateResponse disableComment(Long id, Session session) {
        websiteBlogCommentDao.updateState(id,0);
        return new WebsiteBlogCommentUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 启用博客评论
     * @param id
     * @param session
     * @return
     */
    @Override
    public WebsiteBlogCommentUpdateResponse enableComment(Long id, Session session) {
        websiteBlogCommentDao.updateState(id,1);
        return new WebsiteBlogCommentUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Override
    @Transactional(readOnly = false)
    public void followComment(Long id) {
        websiteBlogCommentDao.followComment(id);
    }

    @Override
    public Long countWebsiteRemarkByBlogId(Long blogId) {
        return websiteBlogCommentDao.countWebsiteRemarkByBlogId(blogId);
    }

    @Override
    public List<WebsiteBlogComment> listComment(Long id) {
        List<WebsiteBlogComment> websiteBlogComments = websiteBlogCommentDao.listComment(id);
        return websiteBlogComments;
    }
}