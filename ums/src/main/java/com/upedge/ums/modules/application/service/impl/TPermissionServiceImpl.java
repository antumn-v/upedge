package com.upedge.ums.modules.application.service.impl;

import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.exception.CustomerException;
import com.upedge.common.model.user.vo.MenuVo;
import com.upedge.common.model.user.vo.PermissionVo;
import com.upedge.ums.modules.application.entity.Menu;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.application.dao.TPermissionDao;
import com.upedge.ums.modules.application.entity.TPermission;
import com.upedge.ums.modules.application.service.TPermissionService;


@Service
public class TPermissionServiceImpl implements TPermissionService {

    @Autowired
    private TPermissionDao tPermissionDao;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        TPermission record = new TPermission();
        record.setId(id);
        return tPermissionDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(TPermission record) {
        return tPermissionDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(TPermission record) {
        return tPermissionDao.insert(record);
    }

    @Override
    public List<PermissionVo> selectByMenuId(Long menuId) {
        List<TPermission> tPermissions = tPermissionDao.selectByMenuId(menuId);
        try {
            return treePermissions(tPermissions);
        } catch (CustomerException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<PermissionVo> treePermission() throws CustomerException {
        Page<TPermission> page = new Page<>();
        page.setPageSize(-1);
        List<TPermission> permissions = select(page);
        return treePermissions(permissions);
    }

    /**
     *
     */
    public TPermission selectByPrimaryKey(Long id){
        TPermission record = new TPermission();
        record.setId(id);
        return tPermissionDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(TPermission record) {
        return tPermissionDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(TPermission record) {
        return tPermissionDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<TPermission> select(Page<TPermission> record){
        record.initFromNum();
        return tPermissionDao.select(record);
    }

    /**
    *
    */
    public long count(Page<TPermission> record){
        return tPermissionDao.count(record);
    }


    public List<PermissionVo> treePermissions(List<TPermission> list) throws CustomerException {
        // 初始化结果
        List<PermissionVo> result = new ArrayList<PermissionVo>();
        // 初始化索引
        Map<Long, PermissionVo> map = new HashMap<Long,PermissionVo>();
        // 死循环阈值
        int maxLoop = (list.size() + 1)*list.size()/2;
        while(list.size() > 0) {
            TPermission permission = list.get(0);
            PermissionVo permissionVo = new PermissionVo();
            permissionVo.setChildren(new ArrayList<>());
            BeanUtils.copyProperties(permission,permissionVo);
            // 一级节点
            if(permission.getParentId().longValue() == 0L) {
                result.add(permissionVo);
                map.put(permission.getId(),permissionVo);
                list.remove(0);
            }
            else {
                //其他节点
                if(map.containsKey(permission.getParentId())) {
                    //父节点地址在map中有记录
                    PermissionVo parent = map.get(permission.getParentId());
                    parent.getChildren().add(permissionVo);
                    map.put(permission.getId(), permissionVo);
                    list.remove(0);
                }
                else {
                    // 如果没有找到父节点就将其移动到队列的末尾
                    list.remove(0);
                    list.add(permission);
                }
            }
            if(maxLoop-- < 0) {
                //当循环到此时，说明有孤立节点，终止循环
                throw new CustomerException(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
            }
        }

        return result;
    }

}