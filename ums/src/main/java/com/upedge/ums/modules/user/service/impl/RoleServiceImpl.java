package com.upedge.ums.modules.user.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.MenuVo;
import com.upedge.common.model.user.vo.RoleVo;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.ListUtils;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.enums.ApplicationDefaultRole;
import com.upedge.ums.modules.application.entity.Menu;
import com.upedge.ums.modules.user.entity.RoleMenu;
import com.upedge.ums.modules.user.entity.RolePermission;
import com.upedge.ums.modules.user.request.RoleAddRequest;
import com.upedge.ums.modules.user.request.RoleUpdateRequest;
import com.upedge.ums.modules.user.service.RoleMenuService;
import com.upedge.ums.modules.user.service.RolePermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.user.dao.RoleDao;
import com.upedge.ums.modules.user.entity.Role;
import com.upedge.ums.modules.user.service.RoleService;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    RoleMenuService roleMenuService;

    @Autowired
    RolePermissionService rolePermissionService;

    @Autowired
    RedisTemplate redisTemplate;


    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Role record = new Role();
        record.setId(id);
        rolePermissionService.deleteByPrimaryKey(id);
        roleMenuService.deleteByPrimaryKey(id);
        return roleDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Role record) {
        return roleDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Role record) {
        return roleDao.insert(record);
    }

    @Override
    public RoleVo selectRoleInfo(Long roleId) {
        Session session = UserUtil.getSession(redisTemplate);
        Role role = selectByPrimaryKey(roleId);
        if (null != role){
            RoleVo roleVo = new RoleVo();
            BeanUtils.copyProperties(role,roleVo);
            List<Menu> menus = roleMenuService.selectRoleMenuByApplication(roleId, session.getApplicationId());
            List<MenuVo> menuVos = menus.stream().map(menu -> {
                MenuVo menuVo = new MenuVo();
                BeanUtils.copyProperties(menu, menuVo);
                return menuVo;
            }).collect(Collectors.toList());
            roleVo.setMenus(menuVos);
            List<String> permissions = rolePermissionService.selectPermissionByRole(roleId);
            roleVo.setPermissions(permissions);
            return roleVo;
        }
        return null;
    }

    @Transactional
    @Override
    public BaseResponse addRole(RoleAddRequest request, Session session) {

        Role entity=request.toRole(session);
        insertSelective(entity);
        refreshRoleMenus(entity.getId(),request.getMenuIds());
        refreshRolePermissions(entity.getId(),request.getPermissions());
        return BaseResponse.success();
    }

    @Override
    public BaseResponse updateRole(RoleUpdateRequest request, Long id) {
        Role entity=request.toRole(id);
        updateByPrimaryKeySelective(entity);
        refreshRoleMenus(id,request.getMenuIds());
        refreshRolePermissions(id,request.getPermissions());
        return BaseResponse.success();
    }


    void refreshRoleMenus(Long roleId,List<Long> menuIds){
        roleMenuService.deleteByPrimaryKey(roleId);
        if (ListUtils.isNotEmpty(menuIds)){
            List<RoleMenu> roleMenus = new ArrayList<>();
            for (Long menuId : menuIds) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(menuId);
                roleMenu.setRoleId(roleId);
                roleMenus.add(roleMenu);
            }
            roleMenuService.batchInsert(roleMenus);
        }
    }

    void refreshRolePermissions(Long roleId,List<String> permissions){
        rolePermissionService.deleteByPrimaryKey(roleId);
        if (ListUtils.isNotEmpty(permissions)){
            List<RolePermission> rolePermissions = new ArrayList<>();
            for (String permission : permissions) {
                RolePermission rolePermission = new RolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermission(permission);
                rolePermissions.add(rolePermission);
            }
            rolePermissionService.batchInsert(rolePermissions);
        }
    }

    @Override
    public  Role selectRoleByUser(Long userId) {
        return roleDao.selectRoleByUser(userId);
    }

    @Override
    public Role selectApplicationDefaultRole(Long applicationId) {
        for (ApplicationDefaultRole value : ApplicationDefaultRole.values()) {
            while (value.getApplicationId().equals(applicationId)){
                Role role = roleDao.selectByApplicationIdAndRoleCode(applicationId,value.getRoleCode());
                return role;
            }
        }
        return null;

    }

    /**
     *
     */
    public Role selectByPrimaryKey(Long id){
        Role record = new Role();
        record.setId(id);
        return roleDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Role record) {
        return roleDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Role record) {
        return roleDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<Role> select(Page<Role> record){
        record.initFromNum();
        return roleDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Role> record){
        return roleDao.count(record);
    }

}