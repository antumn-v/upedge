package com.upedge.ums.modules.manager.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.manager.vo.ManagerInfoVo;
import com.upedge.ums.modules.manager.entity.ManagerInfo;
import com.upedge.ums.modules.manager.vo.ManagerInfoContainsUserInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface ManagerInfoDao{

    ManagerInfo selectManagerByCode(String code);

    ManagerInfo selectByCustomerId(Long customerId);

    List<ManagerInfoVo> selectAllManagerInfos();

    ManagerInfo selectByPrimaryKey(String managerCode);

    int deleteByPrimaryKey(ManagerInfo record);

    int updateByPrimaryKey(ManagerInfo record);

    int updateByPrimaryKeySelective(ManagerInfo record);

    int insert(ManagerInfo record);

    int insertSelective(ManagerInfo record);

    int insertByBatch(List<ManagerInfo> list);

    List<ManagerInfo> select(Page<ManagerInfo> record);

    long count(Page<ManagerInfo> record);

    List<ManagerInfo> getManagerList(@Param("t") ManagerInfo managerInfo);

    String getManagerByOrderSourceId(@Param("orderSourceId") Long orderSourceId);

    int getCountByorderSourceId(@Param("t") ManagerInfo record);

    List<ManagerInfoContainsUserInfoVo> selectContainsUserInfoPage(Page<ManagerInfo> record);

    ManagerInfo selectById(@Param("id") Long id);
}
