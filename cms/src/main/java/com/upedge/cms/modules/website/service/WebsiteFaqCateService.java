package com.upedge.cms.modules.website.service;

import com.upedge.cms.modules.website.entity.WebsiteFaqCate;
import com.upedge.cms.modules.website.request.WebsiteFaqCateAddRequest;
import com.upedge.cms.modules.website.request.WebsiteFaqCateListRequest;
import com.upedge.cms.modules.website.request.WebsiteFaqCateUpdateRequest;
import com.upedge.cms.modules.website.response.WebsiteFaqCateAddResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqCateInfoResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqCateListResponse;
import com.upedge.cms.modules.website.response.WebsiteFaqCateUpdateResponse;
import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteFaqCateService{

    WebsiteFaqCate selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(WebsiteFaqCate record);

    int updateByPrimaryKeySelective(WebsiteFaqCate record);

    int insert(WebsiteFaqCate record);

    int insertSelective(WebsiteFaqCate record);

    List<WebsiteFaqCate> select(Page<WebsiteFaqCate> record);

    long count(Page<WebsiteFaqCate> record);

    WebsiteFaqCateListResponse adminList(WebsiteFaqCateListRequest request);

    WebsiteFaqCateInfoResponse adminInfo(Long id);

    WebsiteFaqCateAddResponse addFaqCate(WebsiteFaqCateAddRequest request, Session session);

    WebsiteFaqCateUpdateResponse updateFaqCate(WebsiteFaqCateUpdateRequest request, Session session);

    WebsiteFaqCateUpdateResponse enableFaqCate(Long id, Session session);

    WebsiteFaqCateUpdateResponse disableFaqCate(Long id, Session session);

    WebsiteFaqCateListResponse allFaqCate();

    List<WebsiteFaqCate> listFaqCate();
}

