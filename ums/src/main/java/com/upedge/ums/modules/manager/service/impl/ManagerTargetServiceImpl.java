package com.upedge.ums.modules.manager.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.base.Page;
import com.upedge.ums.modules.manager.entity.ManagerTarget;
import com.upedge.ums.modules.manager.request.ManagerTargetAddRequest;
import com.upedge.ums.modules.manager.service.ManagerTargetService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ManagerTargetServiceImpl implements ManagerTargetService {
    @Override
    public BaseResponse managerCreateTarget(String month, ManagerTargetAddRequest.ManagerTargetRequestData request) {
        return null;
    }

    @Override
    public ManagerTarget selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public int batchInsert(List<ManagerTarget> managerTargets) {
        return 0;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(ManagerTarget record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKeySelective(ManagerTarget record) {
        return 0;
    }

    @Override
    public int insert(ManagerTarget record) {
        return 0;
    }

    @Override
    public int insertSelective(ManagerTarget record) {
        return 0;
    }

    @Override
    public List<ManagerTarget> select(Page<ManagerTarget> record) {
        return null;
    }

    @Override
    public long count(Page<ManagerTarget> record) {
        return 0;
    }

    @Override
    public List<ManagerTarget> getList(ManagerTarget managerTarget) {
        return null;
    }

    @Override
    public Map targetData() {
        return null;
    }
}
