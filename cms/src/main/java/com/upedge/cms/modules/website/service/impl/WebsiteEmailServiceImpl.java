package com.upedge.cms.modules.website.service.impl;

import com.upedge.cms.modules.website.dao.WebsiteEmailDao;
import com.upedge.cms.modules.website.entity.WebsiteEmail;
import com.upedge.cms.modules.website.request.WebsiteEmailListRequest;
import com.upedge.cms.modules.website.response.WebsiteEmailDelResponse;
import com.upedge.cms.modules.website.response.WebsiteEmailListResponse;
import com.upedge.cms.modules.website.service.WebsiteEmailService;
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
public class WebsiteEmailServiceImpl implements WebsiteEmailService {

    @Autowired
    private WebsiteEmailDao websiteEmailDao;

    /**
     * 客户邮件列表
     * @param request
     * @return
     */
    @Override
    public WebsiteEmailListResponse adminList(WebsiteEmailListRequest request) {
        request.initFromNum();
        if(request.getT()==null){
            request.setT(new WebsiteEmail());
        }
        WebsiteEmail email=request.getT();
        email.setState(1);
        List<WebsiteEmail> results = websiteEmailDao.select(request);
        Long total = websiteEmailDao.count(request);
        request.setTotal(total);
        return new WebsiteEmailListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,results,request);

    }

    /**
     * 删除客户邮件列表
     * @param id
     * @param session
     * @return
     */
    @Transactional
    @Override
    public WebsiteEmailDelResponse delWebsiteEmail(Long id, Session session) {
        websiteEmailDao.updateState(id,0);
        WebsiteEmailDelResponse res = new WebsiteEmailDelResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);
        return res;
    }

    @Override
    @Transactional
    public void save(WebsiteEmail websiteEmail, HttpServletRequest request) {
        websiteEmail.setId(IdGenerate.nextId());
        websiteEmail.setCreateTime(new Date());
        String remoteAddr= IPUtils.getRemoteAddr(request);
        UserAgent userAgent1 = UserAgentUtils.getUserAgent(request);
        String deviceName=userAgent1.getOperatingSystem().getName();
        websiteEmail.setDeviceName(deviceName);
        websiteEmail.setRemoteAddr(remoteAddr);
        websiteEmailDao.insert(websiteEmail);
    }
}