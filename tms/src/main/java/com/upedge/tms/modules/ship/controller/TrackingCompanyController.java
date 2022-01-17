package com.upedge.tms.modules.ship.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.constant.key.RedisKey;
import com.upedge.common.utils.ListUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api(tags = "物流商公司管理")
@RestController
@RequestMapping("/trackingCompany")
public class TrackingCompanyController {

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("增加物流公司")
    @PostMapping("/add")
    public BaseResponse addTracingCompany(@RequestParam String company){
        List<String> trackingCompanies = (List<String>) redisTemplate.opsForValue().get(RedisKey.STRING_TRACKING_COMPANY);
        if (trackingCompanies.contains(company)){
            return BaseResponse.success();
        }
        trackingCompanies.add(company);
        redisTemplate.opsForValue().set(RedisKey.STRING_TRACKING_COMPANY,trackingCompanies);
        return BaseResponse.success();
    }

    @ApiOperation("删除物流公司")
    @PostMapping("/delete")
    public BaseResponse deleteTrackingCompany(@RequestParam String company){
        List<String> trackingCompanies = (List<String>) redisTemplate.opsForValue().get(RedisKey.STRING_TRACKING_COMPANY);
        if (!trackingCompanies.contains(company)){
            return BaseResponse.success();
        }
        trackingCompanies.remove(company);
        redisTemplate.opsForValue().set(RedisKey.STRING_TRACKING_COMPANY,trackingCompanies);
        return BaseResponse.success();
    }

    @ApiOperation("查询物流公司")
    @GetMapping("/list")
    public BaseResponse listTrackingCompanies(@RequestParam String company){
        List<String> trackingCompanies = (List<String>) redisTemplate.opsForValue().get(RedisKey.STRING_TRACKING_COMPANY);
        if (ListUtils.isNotEmpty(trackingCompanies)){
            return BaseResponse.success(trackingCompanies);
        }
        return BaseResponse.success(new ArrayList<>());
    }
}
