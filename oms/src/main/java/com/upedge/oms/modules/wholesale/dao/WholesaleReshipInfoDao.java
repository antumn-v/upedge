package com.upedge.oms.modules.wholesale.dao;

import com.upedge.common.base.Page;
import com.upedge.oms.modules.wholesale.entity.WholesaleReshipInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface WholesaleReshipInfoDao{

    WholesaleReshipInfo selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(WholesaleReshipInfo record);

    int updateByPrimaryKey(WholesaleReshipInfo record);

    int updateByPrimaryKeySelective(WholesaleReshipInfo record);

    int insert(WholesaleReshipInfo record);

    int insertSelective(WholesaleReshipInfo record);

    int insertByBatch(List<WholesaleReshipInfo> list);

    List<WholesaleReshipInfo> select(Page<WholesaleReshipInfo> record);

    long count(Page<WholesaleReshipInfo> record);

    void updateReshipTimes(@Param("ids") List<Long> ids, @Param("reshipTimes") int reshipTimes);

    List<WholesaleReshipInfo> listOrderReshipInfoByIds(@Param("ids") List<Long> reshipIds);
}
