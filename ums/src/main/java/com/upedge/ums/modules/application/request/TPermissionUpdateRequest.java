package com.upedge.ums.modules.application.request;

import com.upedge.ums.modules.application.entity.TPermission;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class TPermissionUpdateRequest{

    /**
     * 
     */
    private Long parentId;

    private Long menuId;
    /**
     * 
     */
    private String name;
    /**
     * 
     */
    private Integer type;
    /**
     * 
     */
    private String description;

    public TPermission toTPermission(Long id){
        TPermission tPermission=new TPermission();
        tPermission.setId(id);
        tPermission.setParentId(parentId);
        tPermission.setMenuId(menuId);
        tPermission.setName(name);
        tPermission.setType(type);
        tPermission.setDescription(description);
        return tPermission;
    }

}
