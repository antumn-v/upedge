package com.upedge.ums.modules.application.request;

import com.upedge.common.base.Page;
import com.upedge.common.model.user.vo.Session;
import com.upedge.ums.modules.application.entity.Menu;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class MenuAddRequest{

    /**
    * 应用id
    */
    private Long applicationId;
    /**
    * 
    */
    private String title;
    /**
    * 
    */
    private String name;
    /**
    * 
    */
    private String url;
    /**
    * 
    */
    private Long parentId;
    /**
    * 
    */
    private String menuPath;
    /**
    * 
    */
    private Integer seq;
    /**
    * 
    */
    private Date updateTime;
    /**
    * 
    */
    private Date createTime;
    /**
    * 1:普通用户 2:管理员 3:超级管理员
    */
    private Integer menuType;
    /**
    * 菜单分组
    */
    private String menuGroup;
    /**
    * 权限名
    */
    private String permissionName;
    /**
    * 0=页面权限，1=按钮权限
    */
    private Integer permissionType;
    /**
    * 0=禁用 1=启用
    */
    private Integer state;
    /**
    * 
    */
    private Boolean createPerm;

    public Menu toMenu(){
        Menu menu=new Menu();
        menu.setApplicationId(applicationId);
        menu.setTitle(title);
        menu.setName(name);
        menu.setUrl(url);
        menu.setParentId(parentId);
        menu.setMenuPath(menuPath);
        menu.setSeq(seq);
        menu.setUpdateTime(updateTime);
        menu.setCreateTime(createTime);
        if(menuType == null){
            menuType = 1;
        }
        menu.setMenuType(menuType);
        menu.setMenuGroup(menuGroup);
        menu.setPermissionName(permissionName);
        menu.setPermissionType(permissionType);
        menu.setState(state);
        menu.setCreatePerm(createPerm);
        return menu;
    }

}
