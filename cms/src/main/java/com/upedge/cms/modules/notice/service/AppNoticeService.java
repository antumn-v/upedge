package com.upedge.cms.modules.notice.service;

import com.upedge.cms.modules.notice.entity.AppNotice;
import com.upedge.cms.modules.notice.request.AppNoticeAddRequest;
import com.upedge.cms.modules.notice.request.AppNoticeListRequest;
import com.upedge.cms.modules.notice.request.AppNoticeUpdateRequest;
import com.upedge.cms.modules.notice.response.AppNoticeAddResponse;
import com.upedge.cms.modules.notice.response.AppNoticeInfoResponse;
import com.upedge.cms.modules.notice.response.AppNoticeListResponse;
import com.upedge.cms.modules.notice.response.AppNoticeUpdateResponse;
import com.upedge.common.model.user.vo.Session;

/**
 * @author author
 */
public interface AppNoticeService{

    AppNotice selectRecentlyNotice();

    int addViewCountById(Long id);

    AppNoticeListResponse adminList(AppNoticeListRequest request);

    AppNoticeInfoResponse adminInfo(Long id);

    AppNoticeAddResponse addNotice(AppNoticeAddRequest request, Session session);

    AppNoticeUpdateResponse updateNotice(AppNoticeUpdateRequest request, Session session);

    AppNoticeUpdateResponse disableNotice(Long id, Session session);

    AppNoticeUpdateResponse enableNotice(Long id, Session session);
}

