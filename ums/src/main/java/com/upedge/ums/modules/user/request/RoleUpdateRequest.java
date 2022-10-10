package com.upedge.ums.modules.user.request;

import com.upedge.ums.modules.user.entity.Role;
import lombok.Data;

import java.util.Date;
import java.util.List;
/**
 * @author gx
 */
@Data
public class RoleUpdateRequest{

    private Long id;

    /**
     * 
     */
    private Long customerId;
    /**
     * 
     */
    private String roleCode;
    /**
     * 
     */
    private String roleName;
    /**
     * 
     */
    private Date createTime;
    /**
     * 
     */
    private Date updateTime;
    /**
     * 
     */
    private Long applicationId;
    /**
     * 0=应用默认角色，1=部门自定义角色，2=客户自定义角色
     */
    private Integer roleType;

    private List<Long> menuIds;

    private List<String> permissions;

    public Role toRole(Long id){
        Role role=new Role();
        role.setId(id);
        role.setRoleCode(roleCode);
        role.setRoleName(roleName);
        role.setUpdateTime(new Date());
        role.setApplicationId(applicationId);
        role.setRoleType(roleType);
        return role;
    }

}
