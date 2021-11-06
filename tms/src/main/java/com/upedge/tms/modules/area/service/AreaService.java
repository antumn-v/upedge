package com.upedge.tms.modules.area.service;

import com.upedge.common.base.Page;
import com.upedge.common.model.ship.request.AreaListAreaRequest;
import com.upedge.common.model.ship.request.AreaSelectRequest;
import com.upedge.common.model.ship.vo.AreaVo;
import com.upedge.tms.modules.area.entity.Area;
import com.upedge.tms.modules.area.response.AreaListResponse;

import java.util.List;

/**
 * @author author
 */
public interface AreaService{

    AreaVo selectByEntity(AreaSelectRequest request);

    Area selectByPrimaryKey(Long id);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKey(Area record);

    int updateByPrimaryKeySelective(Area record);

    int insert(Area record);

    int insertSelective(Area record);

    List<Area> select(Page<Area> record);

    long count(Page<Area> record);

    Area getRegionByName(String name);

    List<Area> allArea();

    AreaListResponse listArea(AreaListAreaRequest request);
}

