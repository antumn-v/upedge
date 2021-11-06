package com.upedge.tms.modules.area.request;

import com.upedge.tms.modules.area.entity.Area;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author author
 */
@Data
public class AreaAddRequest{

    /**
    * 地区名称
    */
    @NotBlank
    private String name;
    /**
    * 英文名
    */
    @NotBlank
    private String enName;
    /**
    * 地区代码
    */
    @NotBlank
    private String areaCode;
            
    public Area toArea(){
        Area area=new Area();
        area.setName(name);
        area.setEnName(enName);
        area.setAreaCode(areaCode);
        area.setUpdateTime(new Date());
        return area;
    }

}
