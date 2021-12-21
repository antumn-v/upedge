package com.upedge.cms.modules.website.service;

import com.upedge.cms.modules.website.entity.WebsiteFaqInfo;
import com.upedge.cms.modules.website.request.WebsiteFaqInfoAddRequest;
import com.upedge.cms.modules.website.request.WebsiteFaqInfoListRequest;
import com.upedge.cms.modules.website.request.WebsiteFaqInfoUpdateRequest;
import com.upedge.cms.modules.website.response.WebsiteFaqInfoAddResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqInfoInfoResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqInfoListResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqInfoUpdateResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteFaqInfoService{

    WebsiteFaqInfo selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WebsiteFaqInfo record);

    int updateByPrimaryKeySelective(WebsiteFaqInfo record);

    int insert(WebsiteFaqInfo record);

    int insertSelective(WebsiteFaqInfo record);

    List<WebsiteFaqInfo> select(Page<WebsiteFaqInfo> record);

    long count(Page<WebsiteFaqInfo> record);

    WebsiteFaqInfoListResponse adminList(WebsiteFaqInfoListRequest request);

    WebsiteFaqInfoInfoResponse adminInfo(Long id);

    WebsiteFaqInfoAddResponse addFaqInfo(WebsiteFaqInfoAddRequest request, Session session);

    WebsiteFaqInfoUpdateResponse updateFaqInfo(WebsiteFaqInfoUpdateRequest request, Session session);

    WebsiteFaqInfoUpdateResponse enableFaqInfo(Long id, Session session);

    WebsiteFaqInfoUpdateResponse disableFaqInfo(Long id, Session session);

    List<WebsiteFaqInfo> listFaqInfo(String cateId);

    List<WebsiteFaqInfo> searchFaqInfo(String info);
}

