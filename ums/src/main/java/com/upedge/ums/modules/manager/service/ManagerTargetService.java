package com.upedge.ums.modules.manager.service;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.ums.modules.manager.entity.ManagerTarget;
import com.upedge.ums.modules.manager.request.ManagerTargetAddRequest;

import java.util.List;
import java.util.Map;

/**
 * @author author
 */
public interface ManagerTargetService{

    BaseResponse managerCreateTarget(String month, ManagerTargetAddRequest.ManagerTargetRequestData request);

    ManagerTarget selectByPrimaryKey(Integer id);

    int batchInsert(List<ManagerTarget> managerTargets);

    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKey(ManagerTarget record);

    int updateByPrimaryKeySelective(ManagerTarget record);

    int insert(ManagerTarget record);

    int insertSelective(ManagerTarget record);

    List<ManagerTarget> select(Page<ManagerTarget> record);

    long count(Page<ManagerTarget> record);

    /**
     * 查到上个月（ManagerTarget）客户经理目标集合
     * @param managerTarget
     * @return
     */
    List<ManagerTarget> getList(ManagerTarget managerTarget);

    Map targetData();
}

