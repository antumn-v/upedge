package com.upedge.cms.modules.website.service.impl;

import com.upedge.cms.modules.website.dao.WebsiteFaqInfoDao;
import com.upedge.cms.modules.website.entity.WebsiteFaqInfo;
import com.upedge.cms.modules.website.request.WebsiteFaqInfoAddRequest;
import com.upedge.cms.modules.website.request.WebsiteFaqInfoListRequest;
import com.upedge.cms.modules.website.request.WebsiteFaqInfoUpdateRequest;
import com.upedge.cms.modules.website.response.*;
import com.upedge.cms.modules.website.service.WebsiteFaqInfoService;
import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IdGenerate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class WebsiteFaqInfoServiceImpl implements WebsiteFaqInfoService {

    @Autowired
    private WebsiteFaqInfoDao websiteFaqInfoDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WebsiteFaqInfo record = new WebsiteFaqInfo();
        record.setId(id);
        return websiteFaqInfoDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WebsiteFaqInfo record) {
        return websiteFaqInfoDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WebsiteFaqInfo record) {
        return websiteFaqInfoDao.insert(record);
    }

    /**
     *
     */
    public WebsiteFaqInfo selectByPrimaryKey(Long id){
        return websiteFaqInfoDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(WebsiteFaqInfo record) {
        return websiteFaqInfoDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WebsiteFaqInfo record) {
        return websiteFaqInfoDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WebsiteFaqInfo> select(Page<WebsiteFaqInfo> record){
        record.initFromNum();
        return websiteFaqInfoDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WebsiteFaqInfo> record){
        return websiteFaqInfoDao.count(record);
    }

    /**
     * faq内容列表
     * @param request
     * @return
     */
    @Override
    public WebsiteFaqInfoListResponse adminList(WebsiteFaqInfoListRequest request) {
        request.initFromNum();
        List<WebsiteFaqInfo> results = websiteFaqInfoDao.select(request);
        Long total = websiteFaqInfoDao.count(request);
        request.setTotal(total);
        return new WebsiteFaqInfoListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,request);
    }

    /**
     * faq内容详情
     * @param id
     * @return
     */
    @Override
    public WebsiteFaqInfoInfoResponse adminInfo(Long id) {
        WebsiteFaqInfo result = websiteFaqInfoDao.selectByPrimaryKey(id);
        return new WebsiteFaqInfoInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
    }

    /**
     * 添加faq内容
     * @param request
     * @param session
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public WebsiteFaqInfoAddResponse addFaqInfo(WebsiteFaqInfoAddRequest request, Session session) {
        WebsiteFaqInfo entity=request.toWebsiteFaqInfo(String.valueOf(session.getId()));
        entity.setId(IdGenerate.nextId());
        websiteFaqInfoDao.insert(entity);
        return new WebsiteFaqInfoAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
    }

    /**
     * 更新faq内容
     * @param request
     * @param session
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public WebsiteFaqInfoUpdateResponse updateFaqInfo(WebsiteFaqInfoUpdateRequest request, Session session) {
        WebsiteFaqInfo entity=request.toWebsiteFaqInfo(request.getId(),String.valueOf(session.getId()));
        websiteFaqInfoDao.updateByPrimaryKeySelective(entity);
        return new WebsiteFaqInfoUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 启用faq内容
     * @param id
     * @param session
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public WebsiteFaqInfoUpdateResponse enableFaqInfo(Long id, Session session) {
        WebsiteFaqInfo websiteFaqInfo=new WebsiteFaqInfo();
        websiteFaqInfo.setId(id);
        websiteFaqInfo.setState(1);
        websiteFaqInfoDao.updateState(websiteFaqInfo);
        return new WebsiteFaqInfoUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 禁用faq内容
     * @param id
     * @param session
     * @return
     */
    @Override
    @Transactional(readOnly = false)
    public WebsiteFaqInfoUpdateResponse disableFaqInfo(Long id, Session session) {
        WebsiteFaqInfo websiteFaqInfo=new WebsiteFaqInfo();
        websiteFaqInfo.setId(id);
        websiteFaqInfo.setState(0);
        websiteFaqInfoDao.updateState(websiteFaqInfo);
        return new WebsiteFaqInfoUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Override
    public List<WebsiteFaqInfo> listFaqInfo(String cateId) {
        return websiteFaqInfoDao.listFaqInfo(cateId);
    }

    @Override
    public List<WebsiteFaqInfo> searchFaqInfo(String info) {
        return websiteFaqInfoDao.searchFaqInfo(info);
    }
}