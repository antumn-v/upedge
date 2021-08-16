package com.upedge.ums.modules.user.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.user.entity.Role;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class RoleAddRequest{

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

    public Role toRole(){
        Role role=new Role();
        role.setCustomerId(customerId);
        role.setRoleCode(roleCode);
        role.setRoleName(roleName);
        role.setCreateTime(createTime);
        role.setUpdateTime(updateTime);
        role.setApplicationId(applicationId);
        role.setRoleType(roleType);
        return role;
    }

}
