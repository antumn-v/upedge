package com.upedge.sms.modules.winningProduct.dao;

import com.upedge.sms.modules.winningProduct.entity.WinningProductServiceOrder;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.upedge.common.base.Page;

/**
 * @author gx
 */
public interface WinningProductServiceOrderDao{

    WinningProductServiceOrder selectByPrimaryKey(WinningProductServiceOrder record);

    int deleteByPrimaryKey(WinningProductServiceOrder record);

    int updateByPrimaryKey(WinningProductServiceOrder record);

    int updateByPrimaryKeySelective(WinningProductServiceOrder record);

    int insert(WinningProductServiceOrder record);

    int insertSelective(WinningProductServiceOrder record);

    int insertByBatch(List<WinningProductServiceOrder> list);

    List<WinningProductServiceOrder> select(Page<WinningProductServiceOrder> record);

    long count(Page<WinningProductServiceOrder> record);

}
