package com.upedge.cms.modules.website.service.impl;

import com.upedge.cms.modules.website.dao.WebsiteFaqCateDao;
import com.upedge.cms.modules.website.entity.WebsiteFaqCate;
import com.upedge.cms.modules.website.request.WebsiteFaqCateAddRequest;
import com.upedge.cms.modules.website.request.WebsiteFaqCateListRequest;
import com.upedge.cms.modules.website.request.WebsiteFaqCateUpdateRequest;
import com.upedge.cms.modules.website.response.WebsiteFaqCateAddResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqCateInfoResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqCateListResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqCateUpdateResponse;
import com.upedge.cms.modules.website.service.WebsiteFaqCateService;
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
public class WebsiteFaqCateServiceImpl implements WebsiteFaqCateService {

    @Autowired
    private WebsiteFaqCateDao websiteFaqCateDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        WebsiteFaqCate record = new WebsiteFaqCate();
        record.setId(id);
        return websiteFaqCateDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(WebsiteFaqCate record) {
        return websiteFaqCateDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(WebsiteFaqCate record) {
        return websiteFaqCateDao.insert(record);
    }

    /**
     *
     */
    public WebsiteFaqCate selectByPrimaryKey(Long id){
        return websiteFaqCateDao.selectByPrimaryKey(id);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(WebsiteFaqCate record) {
        return websiteFaqCateDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(WebsiteFaqCate record) {
        return websiteFaqCateDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<WebsiteFaqCate> select(Page<WebsiteFaqCate> record){
        record.initFromNum();
        return websiteFaqCateDao.select(record);
    }

    /**
    *
    */
    public long count(Page<WebsiteFaqCate> record){
        return websiteFaqCateDao.count(record);
    }

    /**
     * FaqCate列表
     * @param request
     * @return
     */
    @Override
    public WebsiteFaqCateListResponse adminList(WebsiteFaqCateListRequest request) {
        request.initFromNum();
        List<WebsiteFaqCate> results = websiteFaqCateDao.select(request);
        Long total = websiteFaqCateDao.count(request);
        request.setTotal(total);
        return new WebsiteFaqCateListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,request);
    }

    /**
     * faqCate详情
     * @param id
     * @return
     */
    @Override
    public WebsiteFaqCateInfoResponse adminInfo(Long id) {
        WebsiteFaqCate result = websiteFaqCateDao.selectByPrimaryKey(id);
        return new WebsiteFaqCateInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
    }

    /**
     * 添加faqCate
     * @param request
     * @param session
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public WebsiteFaqCateAddResponse addFaqCate(WebsiteFaqCateAddRequest request, Session session) {
        WebsiteFaqCate entity=request.toWebsiteFaqCate();
        entity.setId(IdGenerate.nextId());
        websiteFaqCateDao.insert(entity);
        return new WebsiteFaqCateAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
    }

    /**
     * 更新faqCate
     * @param request
     * @param session
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public WebsiteFaqCateUpdateResponse updateFaqCate(WebsiteFaqCateUpdateRequest request, Session session) {
        WebsiteFaqCate entity=request.toWebsiteFaqCate();
        websiteFaqCateDao.updateByPrimaryKeySelective(entity);
        return new WebsiteFaqCateUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 启用faqCate
     * @param id
     * @param session
     * @return
     */
    @Transactional(readOnly = false)
    @Override
    public WebsiteFaqCateUpdateResponse enableFaqCate(Long id, Session session) {
        WebsiteFaqCate websiteFaqCate=new WebsiteFaqCate();
        websiteFaqCate.setId(id);
        websiteFaqCate.setState(1);
        websiteFaqCateDao.updateState(websiteFaqCate);
        return new WebsiteFaqCateUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    /**
     * 禁用faqCate
     * @param id
     * @param session
     * @return
     */
    @Override
    public WebsiteFaqCateUpdateResponse disableFaqCate(Long id, Session session) {
        WebsiteFaqCate websiteFaqCate=new WebsiteFaqCate();
        websiteFaqCate.setId(id);
        websiteFaqCate.setState(0);
        websiteFaqCateDao.updateState(websiteFaqCate);
        return new WebsiteFaqCateUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
    }

    @Override
    public WebsiteFaqCateListResponse allFaqCate() {
        List<WebsiteFaqCate> results = websiteFaqCateDao.allFaqCate();
        return new WebsiteFaqCateListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,null);
    }

    @Override
    public List<WebsiteFaqCate> listFaqCate() {
        return websiteFaqCateDao.listFaqCate();
    }
}