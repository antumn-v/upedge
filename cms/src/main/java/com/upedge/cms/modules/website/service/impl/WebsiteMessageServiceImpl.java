package com.upedge.cms.modules.website.service.impl;

import com.upedge.cms.modules.website.dao.WebsiteMessageDao;
import com.upedge.cms.modules.website.entity.WebsiteMessage;
import com.upedge.cms.modules.website.request.WebsiteMessageAllocateRequest;
import com.upedge.cms.modules.website.request.WebsiteMessageListRequest;
import com.upedge.cms.modules.website.request.WebsiteMessageUpdateRequest;
import com.upedge.cms.modules.website.response.WebsiteMessageDelResponse;
import com.upedge.cms.modules.website.response.WebsiteMessageListResponse;
import com.upedge.cms.modules.website.response.WebsiteMessageUpdateResponse;
import com.upedge.cms.modules.website.service.WebsiteMessageService;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.IPUtils;
import com.upedge.common.utils.IdGenerate;
import com.upedge.common.utils.UserAgentUtils;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Service
public class WebsiteMessageServiceImpl implements WebsiteMessageService {

    @Autowired
    private WebsiteMessageDao websiteMessageDao;

    /**
     * 客户信息列表
     * @param request
     * @return
     */
    @Override
    public WebsiteMessageListResponse adminList(WebsiteMessageListRequest request) {
        request.initFromNum();
        if(request.getT()==null){
            request.setT(new WebsiteMessage());
        }
        WebsiteMessage websiteMessage=request.getT();
        websiteMessage.setState(1);
        List<WebsiteMessage> results = websiteMessageDao.select(request);
        Long total = websiteMessageDao.count(request);
        request.setTotal(total);
        return new WebsiteMessageListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,request);
    }

    /**
     * 删除客户信息
     * @param id
     * @param session
     * @return
     */
    @Transactional
    @Override
    public WebsiteMessageDelResponse delWebsiteMessage(Long id, Session session) {
        websiteMessageDao.updateState(id,0);
        WebsiteMessageDelResponse res = new WebsiteMessageDelResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
        return res;
    }

    /**
     * 更新备注
     * @param request
     * @param session
     * @return
     */
    @Transactional
    @Override
    public WebsiteMessageUpdateResponse updateRemark(WebsiteMessageUpdateRequest request, Session session) {
        WebsiteMessage entity=request.toWebsiteMessage();
        websiteMessageDao.updateByPrimaryKeySelective(entity);
        return new WebsiteMessageUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 分配客户经理
     * @param request
     * @param session
     * @return
     */
    @Override
    public WebsiteMessageUpdateResponse allocate(WebsiteMessageAllocateRequest request, Session session) {
        websiteMessageDao.allocate(request.getIds(),request.getAdminUser());
        return new WebsiteMessageUpdateResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
    }

    /**
     * 客户信息导出
     * @return
     */
    @Override
    public BaseResponse export() {
        WebsiteMessageListRequest request=new WebsiteMessageListRequest();
        WebsiteMessage websiteMessage=new WebsiteMessage();
        websiteMessage.setState(1);
        request.setT(websiteMessage);
        Long total = websiteMessageDao.count(request);
        if(total>5){
            return new BaseResponse(ResultCode.FAIL_CODE, "数据量超出范围!");
        }
        request.setFields("nopage");
        List<WebsiteMessage> results = websiteMessageDao.select(request);
        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,request);
    }

    @Transactional(readOnly=false)
    @Override
    public void save(WebsiteMessage websiteMessage, HttpServletRequest request) {
        websiteMessage.setCreateTime(new Date());
        String remoteAddr= IPUtils.getRemoteAddr(request);
        UserAgent userAgent1 = UserAgentUtils.getUserAgent(request);
        String deviceName=userAgent1.getOperatingSystem().getName();
        websiteMessage.setId(IdGenerate.nextId());
        websiteMessage.setDeviceName(deviceName);
        websiteMessage.setRemoteAddr(remoteAddr);
        websiteMessageDao.insert(websiteMessage);
    }
}