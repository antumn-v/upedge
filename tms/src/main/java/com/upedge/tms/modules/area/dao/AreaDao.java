package com.upedge.tms.modules.area.dao;

import com.upedge.common.base.Page;
import com.upedge.common.model.ship.request.AreaSelectRequest;
import com.upedge.common.model.ship.vo.AreaVo;
import com.upedge.tms.modules.area.entity.Area;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author author
 */
public interface AreaDao{

    AreaVo selectByEntity(AreaSelectRequest request);

    List<Long> selectIdByCountries(@Param("countries") List<String> countries);

    Area selectByPrimaryKey(Area record);

    int deleteByPrimaryKey(Area record);

    int updateByPrimaryKey(Area record);

    int updateByPrimaryKeySelective(Area record);

    int insert(Area record);

    int insertSelective(Area record);

    int insertByBatch(List<Area> list);

    List<Area> select(Page<Area> record);

    long count(Page<Area> record);

    Area getRegionByName(String name);

    List<Area> allArea();

    List<Area> listArea(@Param("ids") List<Long> ids);
}
