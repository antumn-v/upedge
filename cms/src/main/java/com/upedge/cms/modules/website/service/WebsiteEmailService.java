package com.upedge.cms.modules.website.service;

import com.upedge.cms.modules.website.entity.WebsiteEmail;
import com.upedge.cms.modules.website.request.WebsiteEmailListRequest;
import com.upedge.cms.modules.website.response.WebsiteEmailDelResponse;
import com.upedge.cms.modules.website.response.WebsiteEmailListResponse;
import com.upedge.common.model.user.vo.Session;

import javax.servlet.http.HttpServletRequest;

/**
 * @author author
 */
public interface WebsiteEmailService{

    WebsiteEmailListResponse adminList(WebsiteEmailListRequest request);

    WebsiteEmailDelResponse delWebsiteEmail(Long id, Session session);

    void save(WebsiteEmail websiteEmail, HttpServletRequest request);
}

