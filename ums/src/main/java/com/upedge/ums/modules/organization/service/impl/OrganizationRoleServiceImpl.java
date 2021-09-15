package com.upedge.ums.modules.organization.service.impl;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.model.user.vo.RoleVo;
import com.upedge.common.utils.ListUtils;
import com.upedge.ums.modules.organization.entity.Organization;
import com.upedge.ums.modules.organization.request.OrganizationRoleDeleteRequest;
import com.upedge.ums.modules.organization.service.OrganizationService;
import com.upedge.ums.modules.organization.vo.OrganizationRoleVo;
import com.upedge.ums.modules.user.entity.Role;
import com.upedge.ums.modules.user.service.RoleService;
import com.upedge.ums.modules.user.service.UserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import com.upedge.common.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import com.upedge.ums.modules.organization.dao.OrganizationRoleDao;
import com.upedge.ums.modules.organization.entity.OrganizationRole;
import com.upedge.ums.modules.organization.service.OrganizationRoleService;


@Service
public class OrganizationRoleServiceImpl implements OrganizationRoleService {

    @Autowired
    private OrganizationRoleDao organizationRoleDao;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    RoleService roleService;

    @Autowired
    UserRoleService userRoleService;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long orgId) {
        OrganizationRole record = new OrganizationRole();
        record.setOrgId(orgId);
        return organizationRoleDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(OrganizationRole record) {
        Organization organization = organizationService.selectByPrimaryKey(record.getOrgId());
        Role role = roleService.selectByPrimaryKey(record.getRoleId());
        if (null == organization || null == role){
            return 0;
        }
        return organizationRoleDao.insert(record);
    }




    /**
     *
     */
    @Transactional
    public int insertSelective(OrganizationRole record) {
        return organizationRoleDao.insert(record);
    }

    @Override
    public List<OrganizationRoleVo> organizationRoles(Long orgId) {
        List<OrganizationRoleVo> organizationRoleVos = new ArrayList<>();
        Organization organization = organizationService.selectByPrimaryKey(orgId);
        if (organization != null){
            Page<OrganizationRole> page = new Page<>();
            OrganizationRole organizationRole = new OrganizationRole();
            organizationRole.setOrgId(orgId);
            page.setT(organizationRole);
            page.setPageSize(-1);
            List<OrganizationRole> organizationRoles = organizationRoleDao.select(page);
            if (ListUtils.isNotEmpty(organizationRoles)){
                for (OrganizationRole role : organizationRoles) {
                    RoleVo roleVo = roleService.selectRoleInfo(role.getRoleId());
                    if (null != roleVo){
                        OrganizationRoleVo organizationRoleVo = new OrganizationRoleVo();
                        BeanUtils.copyProperties(roleVo,organizationRoleVo);
                        organizationRoleVo.setDataType(organizationRoleVo.getRoleType());
                        organizationRoleVo.setOrgId(orgId);
                        organizationRoleVo.setOrgName(organization.getOrgName());
                        organizationRoleVos.add(organizationRoleVo);
                    }
                }
            }
        }
        return organizationRoleVos;
    }

    /**
     *
     */
    public OrganizationRole selectByPrimaryKey(Long orgId){
        OrganizationRole record = new OrganizationRole();
        record.setOrgId(orgId);
        return organizationRoleDao.selectByPrimaryKey(record);
    }

    @Override
    public BaseResponse deleteOrganizationRole(OrganizationRoleDeleteRequest request) {
        Organization organization = organizationService.selectByPrimaryKey(request.getOrgId());
        Role role = roleService.selectByPrimaryKey(request.getRoleId());
        if (null == organization || null == role){
            return BaseResponse.failed("部门或角色不存在");
        }
        if (role.getRoleType().equals(Role.SYSTEM_DEFAULT_ROLE)){
            return BaseResponse.failed("系统默认角色不能删除");
        }
        List<Long> userIds = userRoleService.selectUserIdByRoleId(role.getId());
        if (ListUtils.isNotEmpty(userIds)){
            return BaseResponse.failed("the user is using the role");
        }
        organizationRoleDao.delectOrganizationRole(request.getOrgId(),request.getRoleId());
        return BaseResponse.success();
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(OrganizationRole record) {
        return organizationRoleDao.updateByPrimaryKeySelective(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(OrganizationRole record) {
        return organizationRoleDao.updateByPrimaryKey(record);
    }

    /**
    *
    */
    public List<OrganizationRole> select(Page<OrganizationRole> record){
        record.initFromNum();
        return organizationRoleDao.select(record);
    }

    /**
    *
    */
    public long count(Page<OrganizationRole> record){
        return organizationRoleDao.count(record);
    }

}