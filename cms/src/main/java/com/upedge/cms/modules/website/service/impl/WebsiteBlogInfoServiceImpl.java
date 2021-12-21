package com.upedge.cms.modules.website.service.impl;

import com.upedge.cms.modules.website.dao.WebsiteBlogInfoDao;
import com.upedge.cms.modules.website.entity.WebsiteBlogInfo;
import com.upedge.cms.modules.website.request.WebsiteBlogInfoAddRequest;
import com.upedge.cms.modules.website.request.WebsiteBlogInfoListRequest;
import com.upedge.cms.modules.website.request.WebsiteBlogInfoUpdateRequest;
import com.upedge.cms.modules.website.response.WebsiteBlogInfoAddResponse;
import com.upedge.cms.modules.website.response.WebsiteBlogInfoInfoResponse;
import com.upedge.cms.modules.website.response.WebsiteBlogInfoListResponse;
import com.upedge.cms.modules.website.response.WebsiteBlogInfoUpdateResponse;
import com.upedge.cms.modules.website.service.WebsiteBlogInfoService;
import com.upedge.cms.modules.website.vo.WebsiteBlogInfoVo;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class WebsiteBlogInfoServiceImpl implements WebsiteBlogInfoService {

    @Autowired
    private WebsiteBlogInfoDao websiteBlogInfoDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WebsiteBlogInfo record = new WebsiteBlogInfo();
        record.setId(id);
        return websiteBlogInfoDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WebsiteBlogInfo record) {
        return websiteBlogInfoDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WebsiteBlogInfo record) {
        return websiteBlogInfoDao.insert(record);
    }

    /**
     *
     */
    public WebsiteBlogInfo selectByPrimaryKey(Long id){
        return websiteBlogInfoDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(WebsiteBlogInfo record) {
        return websiteBlogInfoDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WebsiteBlogInfo record) {
        return websiteBlogInfoDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WebsiteBlogInfo> select(Page<WebsiteBlogInfo> record){
        record.initFromNum();
        return websiteBlogInfoDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WebsiteBlogInfo> record){
        return websiteBlogInfoDao.count(record);
    }

    /**
     * 博客列表
     * @param request
     * @return
     */
    @Override
    public WebsiteBlogInfoListResponse adminList(WebsiteBlogInfoListRequest request) {
        request.initFromNum();
        request.setFields("`id`,`title`,`url_suf`,`admin_user`,`create_time`,`update_time`,`view_num`,`follow_num`,`state`");
        List<WebsiteBlogInfoVo> websiteBlogInfoVoList=new ArrayList<>();
        List<WebsiteBlogInfo> results = websiteBlogInfoDao.select(request);
        results.forEach(websiteBlogInfo -> {
            WebsiteBlogInfoVo websiteBlogInfoVo=new WebsiteBlogInfoVo();
            BeanUtils.copyProperties(websiteBlogInfo,websiteBlogInfoVo);
            websiteBlogInfoVoList.add(websiteBlogInfoVo);
        });
        Long total = websiteBlogInfoDao.count(request);
        request.setTotal(total);
        return new WebsiteBlogInfoListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,websiteBlogInfoVoList,request);
    }

    /**
     * 博客详情
     * @param id
     * @return
     */
    @Override
    public WebsiteBlogInfoInfoResponse adminInfo(Long id) {
        WebsiteBlogInfo result = websiteBlogInfoDao.selectByPrimaryKey(id);
        return new WebsiteBlogInfoInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
    }

    /**
     * 添加博客
     * @param request
     * @param session
     * @return
     */
    @Transactional
    @Override
    public WebsiteBlogInfoAddResponse addBlogInfo(WebsiteBlogInfoAddRequest request, Session session) {
        WebsiteBlogInfo entity=request.toWebsiteBlogInfo(String.valueOf(session.getId()));
        entity.setId(IdGenerate.nextId());
        websiteBlogInfoDao.insert(entity);
        return new WebsiteBlogInfoAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
    }

    /**
     * 更新博客
     * @param request
     * @param session
     * @return
     */
    @Transactional
    @Override
    public WebsiteBlogInfoUpdateResponse updateBlogInfo(WebsiteBlogInfoUpdateRequest request, Session session) {
        WebsiteBlogInfo entity=request.toWebsiteBlogInfo(request.getId(),String.valueOf(session.getId()));
        websiteBlogInfoDao.updateByPrimaryKeySelective(entity);
        return new WebsiteBlogInfoUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 启用博客
     * @param id
     * @param session
     * @return
     */
    @Transactional
    @Override
    public WebsiteBlogInfoUpdateResponse enableBlogInfo(Long id, Session session) {
        WebsiteBlogInfo websiteBlogInfo=new WebsiteBlogInfo();
        websiteBlogInfo.setId(id);
        websiteBlogInfo.setState(1);
        websiteBlogInfoDao.updateState(websiteBlogInfo);
        return new WebsiteBlogInfoUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 禁用博客
     * @param id
     * @param session
     * @return
     */
    @Transactional
    @Override
    public WebsiteBlogInfoUpdateResponse disableBlogInfo(Long id, Session session) {
        WebsiteBlogInfo websiteBlogInfo=new WebsiteBlogInfo();
        websiteBlogInfo.setId(id);
        websiteBlogInfo.setState(0);
        websiteBlogInfoDao.updateState(websiteBlogInfo);
        return new WebsiteBlogInfoUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Override
    public List<String> listRouters() {
        return websiteBlogInfoDao.listRouters();
    }

    @Override
    public List<WebsiteBlogInfo> listBlog() {
        List<WebsiteBlogInfo> websiteBlogInfos = websiteBlogInfoDao.listBlog();
        return websiteBlogInfos;
    }

    @Override
    @Transactional
    public void followBlog(Long id) {
        websiteBlogInfoDao.followBlog(id);
    }

    @Override
    public List<WebsiteBlogInfo> blogSearch(String info) {
        return  websiteBlogInfoDao.blogSearch(info);
    }

    @Override
    public WebsiteBlogInfo queryBlogByUrlSuf(String urlSuf) {
        return websiteBlogInfoDao.queryBlogByUrlSuf(urlSuf);
    }

    @Override
    @Transactional
    public WebsiteBlogInfo viewBlog(Long id) {
        websiteBlogInfoDao.viewBlog(id);
        return websiteBlogInfoDao.queryBlog(id);
    }

    @Override
    public void cancelBlog(Long blogId) {
        websiteBlogInfoDao.cancelBlog(blogId);
    }
}