package com.upedge.tms.modules.area.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.ship.request.AreaListAreaRequest;
import com.upedge.common.model.ship.request.AreaSelectRequest;
import com.upedge.common.model.ship.vo.AreaVo;
import com.upedge.tms.modules.area.entity.Area;
import com.upedge.tms.modules.area.request.AreaAddRequest;
import com.upedge.tms.modules.area.request.AreaListRequest;
import com.upedge.tms.modules.area.request.AreaUpdateRequest;
import com.upedge.tms.modules.area.response.AreaAddResponse;
import com.upedge.tms.modules.area.response.AreaInfoResponse;
import com.upedge.tms.modules.area.response.AreaListResponse;
import com.upedge.tms.modules.area.response.AreaUpdateResponse;
import com.upedge.tms.modules.area.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 地区表
 *
 * @author author
 */
@RestController
@RequestMapping("/area")
public class AreaController {
    @Autowired
    private AreaService areaService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @RequestMapping(value="/list", method=RequestMethod.POST)
    public AreaListResponse list(@RequestBody @Valid AreaListRequest request) {
        List<Area> results = areaService.select(request);
        Long total = areaService.count(request);
        request.setTotal(total);
        AreaListResponse res = new AreaListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @RequestMapping(value="/listArea", method=RequestMethod.POST)
    public AreaListResponse listArea(@RequestBody @Valid AreaListAreaRequest request) {
        return areaService.listArea(request);
    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    public AreaInfoResponse info(@PathVariable Long id) {
        Area result = areaService.selectByPrimaryKey(id);
        AreaInfoResponse res = new AreaInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public AreaAddResponse add(@RequestBody @Valid AreaAddRequest request) {
        Area entity=request.toArea();
        areaService.insertSelective(entity);
        AreaAddResponse res = new AreaAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }

    @RequestMapping(value="/update/{id}", method=RequestMethod.POST)
    public AreaUpdateResponse update(@PathVariable Long id, @RequestBody @Valid AreaUpdateRequest request) {
        Area entity=request.toArea(id);
        areaService.updateByPrimaryKeySelective(entity);
        AreaUpdateResponse res = new AreaUpdateResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS);
        return res;
    }

    @RequestMapping(value="/all", method=RequestMethod.POST)
    public BaseResponse all() throws IllegalAccessException {
        List<Area> results = areaService.allArea();
//        for (Area area: results) {
//            Field[] fields = area.getClass().getDeclaredFields();
//            for (Field f: fields) {
//                f.setAccessible(true);
//                redisTemplate.opsForHash().put(RedisKey.HASH_AREA + area.getEnName(),f.getName(),f.get(area));
//            }
//        }
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results);
    }


    @PostMapping("/select")
    public BaseResponse areaSelect(@RequestBody AreaSelectRequest request){
        AreaVo area = areaService.selectByEntity(request);
        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,area);
    }
}
