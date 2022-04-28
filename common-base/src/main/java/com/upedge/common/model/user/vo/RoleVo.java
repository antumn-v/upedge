package com.upedge.common.model.user.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by guoxing on 2020/11/9.
 */
@Data
public class RoleVo {
    /**
     *
     */
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

    private List<MenuVo> menus;

    private List<String> permissions;
}
