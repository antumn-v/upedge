package com.upedge.tms.modules.area.request;

import com.upedge.tms.modules.area.entity.Area;
import lombok.Data;

import java.util.Date;

/**
 * @author author
 */
@Data
public class AreaUpdateRequest{

    /**
     * 地区名称
     */
    private String name;
    /**
     * 英文名
     */
    private String enName;

    public Area toArea(Long id){
        Area area=new Area();
        area.setId(id);
        area.setName(name);
        area.setEnName(enName);
        area.setUpdateTime(new Date());
        return area;
    }

}
