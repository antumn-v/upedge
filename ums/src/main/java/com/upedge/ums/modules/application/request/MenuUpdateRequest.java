package com.upedge.ums.modules.application.request;

import com.upedge.ums.modules.application.entity.Menu;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class MenuUpdateRequest{

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
     * 
     */
    private Boolean createPerm;

    public Menu toMenu(Long id){
        Menu menu=new Menu();
        menu.setId(id);
        menu.setApplicationId(applicationId);
        menu.setTitle(title);
        menu.setName(name);
        menu.setUrl(url);
        menu.setParentId(parentId);
        menu.setMenuPath(menuPath);
        menu.setSeq(seq);
        menu.setUpdateTime(new Date());
        menu.setCreateTime(new Date());
        menu.setMenuType(menuType);
        menu.setMenuGroup(menuGroup);
        if (createPerm == null){
            createPerm = true;
        }
        menu.setCreatePerm(createPerm);
        return menu;
    }

}
