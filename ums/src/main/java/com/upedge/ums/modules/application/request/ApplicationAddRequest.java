package com.upedge.ums.modules.application.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.application.entity.Application;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class ApplicationAddRequest{

    /**
    * 应用名称
    */
    private String name;
    /**
    * 
    */
    private String code;
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
    private String url;
    /**
    * 菜单分组
    */
    private String menuGroup;

    public Application toApplication(){
        Application application=new Application();
        application.setName(name);
        application.setCode(code);
        application.setCreateTime(createTime);
        application.setUpdateTime(updateTime);
        application.setUrl(url);
        application.setMenuGroup(menuGroup);
        return application;
    }

}
