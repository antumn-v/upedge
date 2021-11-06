package com.upedge.tms.modules.area.service.impl;

import com.upedge.common.base.Page;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.model.ship.request.AreaListAreaRequest;
import com.upedge.common.model.ship.request.AreaSelectRequest;
import com.upedge.common.model.ship.vo.AreaVo;
import com.upedge.tms.modules.area.dao.AreaDao;
import com.upedge.tms.modules.area.entity.Area;
import com.upedge.tms.modules.area.response.AreaListResponse;
import com.upedge.tms.modules.area.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaDao areaDao;

    @Resource
    private RedisTemplate redisTemplate;



    /**
     *
     */
    @Transactional
    public int deleteByPrimaryKey(Long id) {
        Area record = new Area();
        record.setId(id);
        return areaDao.deleteByPrimaryKey(record);
    }

    /**
     *
     */
    @Transactional
    public int insert(Area record) {
        return areaDao.insert(record);
    }

    /**
     *
     */
    @Transactional
    public int insertSelective(Area record) {
         areaDao.insert(record);
         record = areaDao.selectByPrimaryKey(record);
         redisTemplate.opsForSet().add(RedisKey.AREA+record.getId(),record);
         return areaDao.insert(record);
    }

    @Override
    public AreaVo selectByEntity(AreaSelectRequest request) {
        return areaDao.selectByEntity(request);
    }

    /**
     *
     */
    public Area selectByPrimaryKey(Long id){
        Area record = new Area();
        record.setId(id);
        return areaDao.selectByPrimaryKey(record);
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKeySelective(Area record) {
        int i = areaDao.updateByPrimaryKeySelective(record);
        if (i == 1){
            record = areaDao.selectByPrimaryKey(record);
            redisTemplate.opsForSet().add(RedisKey.AREA+record.getId(),record);
        }
        return i;
    }

    /**
    *
    */
    @Transactional
    public int updateByPrimaryKey(Area record) {
        int i = areaDao.updateByPrimaryKey(record);
        if (i == 1){
            record = areaDao.selectByPrimaryKey(record);
            redisTemplate.opsForSet().add(RedisKey.AREA+record.getId(),record);
        }
        return i;
    }

    /**
    *
    */
    public List<Area> select(Page<Area> record){
        record.initFromNum();
        return areaDao.select(record);
    }

    /**
    *
    */
    public long count(Page<Area> record){
        return areaDao.count(record);
    }

    @Override
    public Area getRegionByName(String name) {
        return areaDao.getRegionByName(name);
    }

    @Override
    public List<Area> allArea() {
        return areaDao.allArea();
    }

    @Override
    public AreaListResponse listArea(AreaListAreaRequest request) {
        List<Area> areaList=areaDao.listArea(request.getIds());
        return new AreaListResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS,areaList,null);
    }
}