package com.upedge.ums.modules.affiliate.service;

import com.upedge.ums.modules.affiliate.entity.Affiliate;
import com.upedge.common.base.Page;
import java.util.List;

/**
 * @author gx
 */
public interface AffiliateService{

    Affiliate selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Affiliate record);

    int updateByPrimaryKeySelective(Affiliate record);

    int insert(Affiliate record);

    int insertSelective(Affiliate record);

    List<Affiliate> select(Page<Affiliate> record);

    long count(Page<Affiliate> record);
}

