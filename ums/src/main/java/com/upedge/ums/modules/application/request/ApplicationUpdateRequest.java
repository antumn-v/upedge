package com.upedge.ums.modules.application.request;

import com.upedge.ums.modules.application.entity.Application;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class ApplicationUpdateRequest{

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

    public Application toApplication(Long id){
        Application application=new Application();
        application.setId(id);
        application.setName(name);
        application.setCode(code);
        application.setCreateTime(createTime);
        application.setUpdateTime(updateTime);
        application.setUrl(url);
        application.setMenuGroup(menuGroup);
        return application;
    }

}
