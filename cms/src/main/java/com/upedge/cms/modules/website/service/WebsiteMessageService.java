package com.upedge.cms.modules.website.service;

import com.upedge.cms.modules.website.entity.WebsiteMessage;
import com.upedge.cms.modules.website.request.WebsiteMessageAllocateRequest;
import com.upedge.cms.modules.website.request.WebsiteMessageListRequest;
import com.upedge.cms.modules.website.request.WebsiteMessageUpdateRequest;
import com.upedge.cms.modules.website.response.WebsiteMessageDelResponse;
import com.upedge.cms.modules.website.response.WebsiteMessageListResponse;
import com.upedge.cms.modules.website.response.WebsiteMessageUpdateResponse;
import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.Session;

import javax.servlet.http.HttpServletRequest;

/**
 * @author author
 */
public interface WebsiteMessageService{

    WebsiteMessageListResponse adminList(WebsiteMessageListRequest request);

    WebsiteMessageDelResponse delWebsiteMessage(Long id, Session session);

    WebsiteMessageUpdateResponse updateRemark(WebsiteMessageUpdateRequest request, Session session);

    WebsiteMessageUpdateResponse allocate(WebsiteMessageAllocateRequest request, Session session);

    BaseResponse export();

    void save(WebsiteMessage websiteMessage, HttpServletRequest request);
}

