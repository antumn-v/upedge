package com.upedge.ums.modules.application.request;

import com.upedge.common.base.Page;
import com.upedge.ums.modules.application.entity.TPermission;
import java.util.Date;
import lombok.Data;
/**
 * @author gx
 */
@Data
public class TPermissionAddRequest{

    /**
    * 
    */
    private Long parentId;
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

    public TPermission toTPermission(){
        TPermission tPermission=new TPermission();
        tPermission.setParentId(parentId);
        tPermission.setName(name);
        tPermission.setType(type);
        tPermission.setDescription(description);
        return tPermission;
    }

}
