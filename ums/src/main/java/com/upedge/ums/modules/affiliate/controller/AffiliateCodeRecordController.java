package com.upedge.ums.modules.affiliate.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.affiliate.entity.AffiliateCodeRecord;
import com.upedge.ums.modules.affiliate.request.AffiliateCodeRecordAddRequest;
import com.upedge.ums.modules.affiliate.request.AffiliateCodeRecordListRequest;
import com.upedge.ums.modules.affiliate.response.AffiliateCodeRecordAddResponse;
import com.upedge.ums.modules.affiliate.response.AffiliateCodeRecordListResponse;
import com.upedge.ums.modules.affiliate.service.AffiliateCodeRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author gx
 */
@Api(tags = "客户邀请码更新管理")
@RestController
@RequestMapping("/affiliateCodeRecord")
public class AffiliateCodeRecordController {
    @Autowired
    private AffiliateCodeRecordService affiliateCodeRecordService;

    @Autowired
    RedisTemplate redisTemplate;

    @ApiOperation("更新记录")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliatecoderecord:list")
    public AffiliateCodeRecordListResponse list(@RequestBody @Valid AffiliateCodeRecordListRequest request) {
        List<AffiliateCodeRecord> results = affiliateCodeRecordService.select(request);
        Long total = affiliateCodeRecordService.count(request);
        request.setTotal(total);
        AffiliateCodeRecordListResponse res = new AffiliateCodeRecordListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @ApiOperation("新增")
    @RequestMapping(value="/add", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliatecoderecord:add")
    public BaseResponse add(@RequestBody @Valid AffiliateCodeRecordAddRequest request) {
        Session session = UserUtil.getSession(redisTemplate);
        AffiliateCodeRecord affiliateCodeRecord = affiliateCodeRecordService.selectByPrimaryKey(request.getToken());
        if (affiliateCodeRecord != null){
            return BaseResponse.failed("Affiliate code cannot be repeated !");
        }
        AffiliateCodeRecord entity=request.toAffiliateCodeRecord(session.getCustomerId());
        affiliateCodeRecordService.insertSelective(entity);
        AffiliateCodeRecordAddResponse res = new AffiliateCodeRecordAddResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,entity,request);
        return res;
    }



}
