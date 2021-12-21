package com.upedge.cms.modules.website.dao;

import com.upedge.cms.modules.website.entity.WebsiteMessage;
import com.upedge.common.base.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface WebsiteMessageDao{

    WebsiteMessage selectByPrimaryKey(WebsiteMessage record);

    int deleteByPrimaryKey(WebsiteMessage record);

    int updateByPrimaryKey(WebsiteMessage record);

    int updateByPrimaryKeySelective(WebsiteMessage record);

    int insert(WebsiteMessage record);

    int insertSelective(WebsiteMessage record);

    int insertByBatch(List<WebsiteMessage> list);

    List<WebsiteMessage> select(Page<WebsiteMessage> record);

    long count(Page<WebsiteMessage> record);

    void updateState(@Param("id") Long id,@Param("state")Integer state);

    void allocate(@Param("ids") List<Long> ids,@Param("adminUser") String adminUser);
}
