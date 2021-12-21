package com.upedge.cms.modules.website.service;

import com.upedge.cms.modules.website.entity.WebsiteRemark;
import com.upedge.cms.modules.website.request.WebsiteRemarkAddRequest;
import com.upedge.cms.modules.website.request.WebsiteRemarkListRequest;
import com.upedge.cms.modules.website.request.WebsiteRemarkUpdateRequest;
import com.upedge.cms.modules.website.response.WebsiteRemarkAddResponse;
import com.upedge.cms.modules.website.response.WebsiteRemarkInfoResponse;
import com.upedge.cms.modules.website.response.WebsiteRemarkListResponse;
import com.upedge.cms.modules.website.response.WebsiteRemarkUpdateResponse;
import com.upedge.common.model.user.vo.Session;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteRemarkService{

    WebsiteRemarkListResponse adminList(WebsiteRemarkListRequest request);

    WebsiteRemarkInfoResponse adminInfo(Long id);

    WebsiteRemarkAddResponse addRemark(WebsiteRemarkAddRequest request, Session session);

    WebsiteRemarkUpdateResponse updateRemark(WebsiteRemarkUpdateRequest request, Session session);

    WebsiteRemarkUpdateResponse enableRemark(Long id, Session session);

    WebsiteRemarkUpdateResponse disableRemark(Long id, Session session);

    WebsiteRemarkUpdateResponse delWebsiteRemark(Long id, Session session);

    List<WebsiteRemark> listWebsiteRemark();
}

