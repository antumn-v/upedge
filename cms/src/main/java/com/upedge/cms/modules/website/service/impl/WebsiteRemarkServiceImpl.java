package com.upedge.cms.modules.website.service.impl;

import com.upedge.cms.modules.website.dao.WebsiteRemarkDao;
import com.upedge.cms.modules.website.entity.WebsiteRemark;
import com.upedge.cms.modules.website.request.WebsiteRemarkAddRequest;
import com.upedge.cms.modules.website.request.WebsiteRemarkListRequest;
import com.upedge.cms.modules.website.request.WebsiteRemarkUpdateRequest;
import com.upedge.cms.modules.website.response.WebsiteRemarkAddResponse;
import com.upedge.cms.modules.website.response.WebsiteRemarkInfoResponse;
import com.upedge.cms.modules.website.response.WebsiteRemarkListResponse;
import com.upedge.cms.modules.website.response.WebsiteRemarkUpdateResponse;
import com.upedge.cms.modules.website.service.WebsiteRemarkService;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WebsiteRemarkServiceImpl implements WebsiteRemarkService {

    @Autowired
    private WebsiteRemarkDao websiteRemarkDao;

    /**
     * 好评列表
     * @param request
     * @return
     */
    @Override
    public WebsiteRemarkListResponse adminList(WebsiteRemarkListRequest request) {
        request.initFromNum();
        List<WebsiteRemark> results = websiteRemarkDao.select(request);
        Long total = websiteRemarkDao.count(request);
        request.setTotal(total);
        return new WebsiteRemarkListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,request);
    }

    /**
     * 好评详情
     * @param id
     * @return
     */
    @Override
    public WebsiteRemarkInfoResponse adminInfo(Long id) {
        return null;
    }

    /**
     * 添加好评
     * @param request
     * @param session
     * @return
     */
    @Transactional
    @Override
    public WebsiteRemarkAddResponse addRemark(WebsiteRemarkAddRequest request, Session session) {
        WebsiteRemark entity=request.toWebsiteRemark();
        entity.setId(IdGenerate.nextId());
        websiteRemarkDao.insert(entity);
        return new WebsiteRemarkAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
    }

    /**
     * 更新好评
     * @param request
     * @param session
     * @return
     */
    @Transactional
    @Override
    public WebsiteRemarkUpdateResponse updateRemark(WebsiteRemarkUpdateRequest request, Session session) {
        WebsiteRemark entity=request.toWebsiteRemark();
        websiteRemarkDao.updateByPrimaryKeySelective(entity);
        return new WebsiteRemarkUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 启用好评
     * @param id
     * @param session
     * @return
     */
    @Transactional
    @Override
    public WebsiteRemarkUpdateResponse enableRemark(Long id, Session session) {
        WebsiteRemark websiteRemark=new WebsiteRemark();
        websiteRemark.setId(id);
        websiteRemark.setState(1);
        websiteRemarkDao.updateState(websiteRemark);
        return new WebsiteRemarkUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 禁用好评
     * @param id
     * @param session
     * @return
     */
    @Transactional
    @Override
    public WebsiteRemarkUpdateResponse disableRemark(Long id, Session session) {
        WebsiteRemark websiteRemark=new WebsiteRemark();
        websiteRemark.setId(id);
        websiteRemark.setState(0);
        websiteRemarkDao.updateState(websiteRemark);
        return new WebsiteRemarkUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 删除好评
     * @param id
     * @param session
     * @return
     */
    @Transactional
    @Override
    public WebsiteRemarkUpdateResponse delWebsiteRemark(Long id, Session session) {
        websiteRemarkDao.deleteByPrimaryKey(id);
        return new WebsiteRemarkUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Override
    public List<WebsiteRemark> listWebsiteRemark() {
        return websiteRemarkDao.listWebsiteRemark();
    }
}