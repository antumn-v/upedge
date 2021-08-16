package com.upedge.ums.modules.affiliate.dao;

import com.upedge.ums.modules.affiliate.entity.Affiliate;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface AffiliateDao{

    Affiliate selectByPrimaryKey(Affiliate record);

    int deleteByPrimaryKey(Affiliate record);

    int updateByPrimaryKey(Affiliate record);

    int updateByPrimaryKeySelective(Affiliate record);

    int insert(Affiliate record);

    int insertSelective(Affiliate record);

    int insertByBatch(List<Affiliate> list);

    List<Affiliate> select(Page<Affiliate> record);

    long count(Page<Affiliate> record);

}
