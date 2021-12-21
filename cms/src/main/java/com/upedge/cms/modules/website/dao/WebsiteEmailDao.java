package com.upedge.cms.modules.website.dao;

import com.upedge.cms.modules.website.entity.WebsiteEmail;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteEmailDao{

    WebsiteEmail selectByPrimaryKey(WebsiteEmail record);

    int deleteByPrimaryKey(WebsiteEmail record);

    int updateByPrimaryKey(WebsiteEmail record);

    int updateByPrimaryKeySelective(WebsiteEmail record);

    int insert(WebsiteEmail record);

    int insertSelective(WebsiteEmail record);

    int insertByBatch(List<WebsiteEmail> list);

    List<WebsiteEmail> select(Page<WebsiteEmail> record);

    long count(Page<WebsiteEmail> record);

    void updateState(@Param("id") Long id, @Param("state")Integer state);
}
