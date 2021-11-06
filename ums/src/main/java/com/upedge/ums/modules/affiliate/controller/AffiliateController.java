package com.upedge.ums.modules.affiliate.controller;

import com.upedge.common.base.BaseResponse;
import com.upedge.common.component.annotation.Permission;
import com.upedge.common.constant.Constant;
import com.upedge.common.constant.ResultCode;
import com.upedge.common.model.user.vo.Session;
import com.upedge.common.utils.CookieUtils;
import com.upedge.common.utils.EncryptUtil;
import com.upedge.common.web.util.RequestUtil;
import com.upedge.common.web.util.UserUtil;
import com.upedge.ums.modules.affiliate.entity.Affiliate;
import com.upedge.ums.modules.affiliate.request.AffiliateListRequest;
import com.upedge.ums.modules.affiliate.response.*;
import com.upedge.ums.modules.affiliate.service.AffiliateService;
import com.upedge.ums.modules.user.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 
 *
 * @author author
 */
@Api(tags = "佣金")
@Slf4j
@RestController
@RequestMapping("/affiliate")
public class AffiliateController {

    private String key = "f167105ef452466f80c97c3b355658a4";

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private AffiliateService affiliateService;

    @Autowired
    CustomerService customerService;

    @ApiOperation("被推荐人佣金列表")
    @RequestMapping(value="/customer/referee", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliate:list")
    public AffiliateListResponse customerRefereeList(@RequestBody @Valid AffiliateListRequest request) {
        if(null == request.getT()){
            request.setT(new Affiliate());
        }

        Session session = UserUtil.getSession(redisTemplate);

        request.getT().setReferrerId(session.getCustomerId());

        List<Affiliate> results = affiliateService.select(request);
        Long total = affiliateService.count(request);
        request.setTotal(total);
        AffiliateListResponse res = new AffiliateListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }

    @ApiOperation("获取联盟推广地址")
    @GetMapping("/bind/url")
    public BaseResponse getAffiliateBindUrl(){
        Session session = UserUtil.getSession(redisTemplate);

        String token = EncryptUtil.XORencode(String.valueOf(session.getCustomerId()), key);

        HttpServletRequest request = RequestUtil.getRequest();

        String url = request.getHeader("Referer") + "?"+ token;

        return new BaseResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,url);
    }

    @ApiOperation("联盟绑定")
    @GetMapping("/bind/{token}")
    public BaseResponse affiliateBind(@PathVariable String token){
        log.warn("联盟注册，推荐人token：" + token);

        Long customerId = null;
        try {
            customerId = (Long.valueOf(EncryptUtil.XORdecode(token, key)));
        } catch (Exception e) {
            return new BaseResponse(ResultCode.FAIL_CODE, "Affiliate Code Error");
        }
        log.warn("联盟注册，推荐人ID：" + customerId);

        HttpServletRequest request = RequestUtil.getRequest();

        boolean b = CookieUtils.addCookie(request, RequestUtil.getResponse(), String.valueOf(customerId));
        if (!b) {
            return new BaseResponse(ResultCode.FAIL_CODE, Constant.MESSAGE_FAIL);
        }

        request.getSession().setAttribute("referrer", customerId);

        return new BaseResponse(ResultCode.SUCCESS_CODE, Constant.MESSAGE_SUCCESS);

    }


    /**
     * 佣金按月统计
     * @return
     */
    @ApiOperation("佣金按月统计")
    @GetMapping("/commission/month/record")
    public AffiliateCommissionRecordListResponse commissionMonthRecord(){
        Session session = UserUtil.getSession(redisTemplate);
        return affiliateService.commissionMonthRecord(session.getCustomerId());
    }


    /**
     * 推荐人佣金
     * @return
     */
    @ApiOperation("推荐人佣金")
    @GetMapping("/referee/commission")
    public AffiliateListResponse refereeCommissions(){
        Session session = UserUtil.getSession(redisTemplate);
        return affiliateService.refereeCommissionList(session.getCustomerId());
    }

    @RequestMapping(value="/info/{id}", method=RequestMethod.GET)
    @Permission(permission = "affiliate:affiliate:info:id")
    public AffiliateInfoResponse info(@PathVariable Long id) {
        Affiliate result = affiliateService.selectByPrimaryKey(id);
        AffiliateInfoResponse res = new AffiliateInfoResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,result,id);
        return res;
    }

    @ApiOperation("联盟列表")
    @RequestMapping(value="/list", method=RequestMethod.POST)
    @Permission(permission = "affiliate:affiliate:list")
    public AffiliateListResponse list(@RequestBody @Valid AffiliateListRequest request) {
        List<Affiliate> results = affiliateService.select(request);
        Long total = affiliateService.count(request);
        request.setTotal(total);
        AffiliateListResponse res = new AffiliateListResponse(ResultCode.SUCCESS_CODE,Constant.MESSAGE_SUCCESS,results,request);
        return res;
    }




}
