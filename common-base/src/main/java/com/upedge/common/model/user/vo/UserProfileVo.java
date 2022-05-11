package com.upedge.common.model.user.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoxing on 2020/11/9.
 */
@Data
public class UserProfileVo {

    private Integer vipLevel;
    /**
     * 用户个人信息
     */
    private UserInfoVo userinfo;

    /**
     * 用户的角色
     */
    private RoleVo roleVo;

    /**
     * 用户的许可
     */
    private List<String> permissions = new ArrayList<String>();

    /**
     * 用户可以访问的菜单
     */
    private List<MenuVo> menus = new ArrayList<MenuVo>();
}
