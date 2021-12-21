package com.upedge.cms.modules.website.dao;

import com.upedge.cms.modules.website.entity.WebsiteFaqInfo;
import com.upedge.common.base.Page;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteFaqInfoDao{

    WebsiteFaqInfo selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(WebsiteFaqInfo record);

    int updateByPrimaryKey(WebsiteFaqInfo record);

    int updateByPrimaryKeySelective(WebsiteFaqInfo record);

    int insert(WebsiteFaqInfo record);

    int insertSelective(WebsiteFaqInfo record);

    int insertByBatch(List<WebsiteFaqInfo> list);

    List<WebsiteFaqInfo> select(Page<WebsiteFaqInfo> record);

    long count(Page<WebsiteFaqInfo> record);

    void updateState(WebsiteFaqInfo websiteFaqInfo);

    List<WebsiteFaqInfo> listFaqInfo(String cateId);

    List<WebsiteFaqInfo> searchFaqInfo(String info);
}
