package com.upedge.cms.modules.website.dao;

import com.upedge.cms.modules.website.entity.WebsiteFaqCate;
import com.upedge.common.base.Page;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteFaqCateDao{

    WebsiteFaqCate selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(WebsiteFaqCate record);

    int updateByPrimaryKey(WebsiteFaqCate record);

    int updateByPrimaryKeySelective(WebsiteFaqCate record);

    int insert(WebsiteFaqCate record);

    int insertSelective(WebsiteFaqCate record);

    int insertByBatch(List<WebsiteFaqCate> list);

    List<WebsiteFaqCate> select(Page<WebsiteFaqCate> record);

    long count(Page<WebsiteFaqCate> record);

    void updateState(WebsiteFaqCate websiteFaqCate);

    List<WebsiteFaqCate> allFaqCate();

    List<WebsiteFaqCate> listFaqCate();
}
